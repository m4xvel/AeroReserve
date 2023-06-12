package com.m4xvel.aeroreserve.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.m4xvel.aeroreserve.R;

public class BookingFragment extends Fragment {

    private final String TAG = "BookingFragment";
    private StorageReference mStorageRef;
    private Uri mImageUri;
    private DatabaseReference mBookingRef;

    private static final String KEY_DATEFROM = "date_from";
    private static final String KEY_DATETO = "date_to";
    private static final String KEY_TIMEFROM = "time_from";
    private static final String KEY_TIMETO = "time_to";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.booking, container, false);

        HomeFragment homeFragment = new HomeFragment();

        mBookingRef = FirebaseDatabase.getInstance().getReference("booking");
        mStorageRef = FirebaseStorage.getInstance().getReference("passports");

        EditText fullNameEditText = rootView.findViewById(R.id.editTextTextPersonName4);
        EditText phoneNumberEditText = rootView.findViewById(R.id.editTextTextPersonName5);
        RadioGroup classRadioGroup = rootView.findViewById(R.id.radio_group);

        Button bookButton = rootView.findViewById(R.id.button12);
        Button chooseImageButton = rootView.findViewById(R.id.button10);

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }

            private void openFileChooser() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                mGetContent.launch(intent);    }
            ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                                mImageUri = result.getData().getData();
                            }
                        }
                    });
                });
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = getArguments();
                String city1 = args.getString("city1");
                String city2 = args.getString("city2");
                String date_from = args.getString(KEY_DATEFROM);
                String date_to = args.getString(KEY_DATETO);
                String time_from = args.getString(KEY_TIMEFROM);
                String time_to = args.getString(KEY_TIMETO);

                String fullName = fullNameEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();
                int selectedClassId = classRadioGroup.getCheckedRadioButtonId();
                String selectedClass;

                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    String email = user.getEmail();

                if(selectedClassId == R.id.radioButton) {
                    selectedClass = "Эконом";
                } else if (selectedClassId == R.id.radioButton2) {
                    selectedClass = "Бизнес";
                } else {
                    selectedClass = "Первый";
                }
                if (fullName.isEmpty() || phoneNumber.isEmpty() || selectedClass.isEmpty()) {
                    Toast.makeText(getActivity(), "Пожалуйста, заполните все данные!", Toast.LENGTH_SHORT).show();
                    return; }
                if (mImageUri != null) {
                    uploadImageAndBooking(fullName, phoneNumber, selectedClass, mImageUri, city1, city2, date_from, date_to, time_from, time_to, email);
                } else {
                    Toast.makeText(getActivity(), "Пожалуйста, загрузите фотографию паспорта!", Toast.LENGTH_SHORT).show();
                }
                } else {
                    Toast.makeText(getActivity(), "Пожалуйста, войдите в аккаунт!", Toast.LENGTH_SHORT).show();
                }
            }
            private void uploadImageAndBooking(String fullName, String phoneNumber, String selectedClass, Uri mImageUri, String city1, String city2, String date_from, String date_to, String time_from, String time_to, String email) {
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Данные отправляются на сервер...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(BookingFragment.this.mImageUri));
                UploadTask uploadTask = fileReference.putFile(BookingFragment.this.mImageUri);
                uploadTask.addOnSuccessListener(taskSnapshot -> {
                            fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                Booking booking = new Booking(fullName, phoneNumber, selectedClass, uri.toString(), city1, city2, date_from, date_to, time_from, time_to, email);
                                String bookingId = mBookingRef.push().getKey();
                                booking.setBookingId(bookingId);
                                mBookingRef.child(bookingId).setValue(booking);
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Заявка будет рассмотрена в ближайшее время!", Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
                            });
                        })
                        .addOnFailureListener(e -> {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        })
                        .addOnProgressListener(taskSnapshot -> {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setProgress((int) progress);
                        });
            }
            private String getFileExtension(Uri uri) {
                ContentResolver contentResolver = getActivity().getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                return mime.getExtensionFromMimeType(contentResolver.getType(uri));
            }
        });
        return rootView;
    }
}
