package com.example.examservice;


import android.content.Context;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyFTPClient {

    private static final String TAG = "TAGMyFTPClient";
    private FTPClient mFTPClient = null;


    private boolean connect() {
        String host = "files1.radiokomunikacja.edu.pl";
        int port = 21;
        try {
            mFTPClient = new FTPClient();
            mFTPClient.connect(host, port);

            if (FTPReply.isPositiveCompletion(mFTPClient.getReplyCode())) {
                String username = "user@files01.radiokomunikacja.edu.pl";
                String password = "M5.wlx.KZH.4";
                boolean status = mFTPClient.login(username, password);
                mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                mFTPClient.enterLocalPassiveMode();

                return status;
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed getting connection to host: " + host);
        }

        return false;
    }

    public boolean disconnect() {
        try {
            mFTPClient.logout();
            mFTPClient.disconnect();
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Failed disconnecting FTPClient.");
        }

        return false;
    }

    public String ftpUpload(String srcFilePath){
        boolean status;
        try {
            File file = new File(srcFilePath);
            FileInputStream srcFileStream = new FileInputStream(file);
            String desFileName = file.getName();
            Log.d(TAG, "adding: " +desFileName);
            status = mFTPClient.storeFile(desFileName, srcFileStream
            );

            srcFileStream.close();

            if(status) return desFileName;
            else return  null ;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "upload failed: " + e);
            return null;
        }
    }

    public boolean ftpDelete(String fileName){

        boolean exist = false;
        try {
            exist = mFTPClient.deleteFile(fileName);
        } catch (IOException e) {
            Log.d(TAG, "Failed deleting file: " + e.getMessage());
            e.printStackTrace();
        }
        if(exist) Log.d(TAG, "Usunieto");
        return exist;
    }

//    public File ftpDownload(String fileName, String contentName, Context context) {
//        //Context context = this;
//        fileName = context.getFilesDir().getPath().toString() + "/" + fileName;
//        File file = new File(fileName);
//        Log.d(TAG, fileName);
//        FileOutputStream outputStream = null;
//        boolean status = false;
//        try{
//            outputStream = new FileOutputStream(file);
//            status = mFTPClient.retrieveFile(contentName, outputStream);
//
//            if(status) return file;
//            else return null ;
//        } catch(IOException e){
//            Log.d(TAG, "Exceptionn: " + e.getMessage());
//            e.printStackTrace();
//        }
//        if(outputStream != null) {
//            try {
//                outputStream.close();
//            } catch (IOException e) {
//                Log.d(TAG, "Exception: " + e.getMessage());
//                e.printStackTrace();
//            }
//        }
//        return null ;
//    }

}
