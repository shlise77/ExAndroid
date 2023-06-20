package com.busanit.ch16_provider

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.busanit.ch16_provider.databinding.ActivityMainBinding
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. 주소록 연동
        val requestContactsLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            if(it.resultCode== RESULT_OK){
                Log.d("myLog", "it.data.data : ${it.data!!.data}")
                val cursor = contentResolver.query(
                    it!!.data!!.data!!, arrayOf<String>(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    ), null, null, null
                )// query(uri, 컬럼명(null일 경우 모든 컬럼), where, 조건절의 내용, 정렬 순서)
                if(cursor!!.moveToFirst()){
                    val name = cursor?.getString(0)
                    val phone = cursor?.getString(1)
                    Log.d("myLog", "name : $name, phone : $phone")
                }
            }
        }
        val btnContacts = binding.btnContacts
        btnContacts.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            requestContactsLauncher.launch(intent)
        }

        // 2. 갤러리 연동
        val btnGallery = binding.btnGallery
        val imgGal = binding.imgGal

        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            try {
                val option = BitmapFactory.Options()
                option.inSampleSize = 2 // 2-분모, 1/2로 출력
                var inputStream = contentResolver.openInputStream(it!!.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream = null
                bitmap?.let {
                    imgGal.setImageBitmap(bitmap)
                } ?: let {
                    Log.d("myLog", "bitmap null")
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }

        btnGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        // 3-1. 촬영 데이터 가져오기
        val btnCamera = binding.btnCamera
        val imgCam = binding.imgCam

//        val requestThumbnailLauncher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()
//        ){
//            val bitmap = it?.data?.extras?.get("data") as Bitmap
//            imgCam.setImageBitmap(bitmap) // 이미지가 작음
//        }
//
//        btnCamera.setOnClickListener {
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            requestThumbnailLauncher.launch(intent)
//        }

        // 3-2. 촬영 파일 공유하기(프로바이더 등록 필요, file_paths.xml 생성, 매니페스토 등록)
        var filePath : String = ""
        val requestFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            val option = BitmapFactory.Options()
            option.inSampleSize = 1
            val bitmap = BitmapFactory.decodeFile(filePath, option)
            bitmap?.let {
                imgCam.setImageBitmap(bitmap)
            }
        }

        btnCamera.setOnClickListener {
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val stroageDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val file = File.createTempFile("JPEG_${timeStamp}", ".jpg", stroageDir)
            filePath = file.absolutePath

            val photoURI : Uri = FileProvider.getUriForFile(this,
            "com.busanit.ch16_provider", file)
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            requestFileLauncher.launch(intent)
        }

        // 4. 지도 연동
        val btnMap = binding.btnMap
        btnMap.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:35.1535, 129.1183"))// 광안리 해수욕장
            startActivity(intent)
        }

        // 5. 전화 연동
        val btnTel = binding.btnTel
        btnTel.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:010-1000-1000"))
            startActivity(intent)
        }
    }
}









