package com.kickhead.bharatscanner_intent_demo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.kunalapk.smartrecyclerview.listener.SmartRecyclerViewListener
import com.kunalapk.smartrecyclerview.view.SmartRecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = javaClass.simpleName

    private val REQUEST_CODE = 200

    private lateinit var smartRecyclerView:SmartRecyclerView<String?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initRecyclerView()
        btnCallBharatScannerIntent?.setOnClickListener(onClickListener)
    }

    private val onClickListener: View.OnClickListener = View.OnClickListener {
        when(it.id){
            R.id.btnCallBharatScannerIntent -> isStoragePermissionGranted()
        }
    }

    private fun callBharatScannerIntent(){
        val bharatScannerIntent = Intent()
        bharatScannerIntent.setClassName(Constants.EXTERNAL_PACKAGENAME, Constants.EXTERNAL_CLASSNAME);
        bharatScannerIntent.putExtra(Constants.KEY_EXTERNAL,true)
        startActivityForResult(bharatScannerIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.extras != null && data.extras!!.containsKey(Constants.SCANNED_IMAGE_LIST)) {
                val images: MutableList<String?>? = data.extras!!.get(Constants.SCANNED_IMAGE_LIST) as MutableList<String?>
                addItemsToGridView(images)
            }
        }
    }

    private fun initRecyclerView(){
        val gridLayoutManager = GridLayoutManager(this,3)
        smartRecyclerView = findViewById(R.id.smartRecyclerView)
        smartRecyclerView.initSmartRecyclerView(activity = this,smartRecyclerViewListener = smartRecyclerViewListener,isPaginated = false,layoutManager = gridLayoutManager)
        smartRecyclerView.apply{
            isEnabled = false
        }
    }

    private val smartRecyclerViewListener:SmartRecyclerViewListener<String?> = object :SmartRecyclerViewListener<String?>{

        override fun getItemViewType(model: String?): Int {
            return 0
        }

        override fun getViewLayout(model: Int): Int {
            return R.layout.item_grid
        }

        override fun onLoadNext() {

        }

        override fun onRefresh() {

        }

        override fun setListSize(size: Int) {

        }

    }


    private fun addItemsToGridView(imageList:MutableList<String?>?){
        if(imageList!=null){
            smartRecyclerView.addItems(imageList)
        }
    }


    fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(baseContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                callBharatScannerIntent()
                true
            } else {
                LogHelper.debug(TAG, "Permission is revoked")
                ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
                false
            }
        }else {
            callBharatScannerIntent()
            LogHelper.debug(TAG, "Permission is granted")
            true
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    callBharatScannerIntent()
                } catch (e: Exception) {

                }
            }
        }
    }
}