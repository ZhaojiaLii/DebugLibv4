package com.example.testlib

import net.lingala.zip4j.core.ZipFile
import net.lingala.zip4j.exception.ZipException
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.util.Zip4jConstants
import org.apache.commons.lang3.StringUtils
import java.io.File

open class ZipFile {

    open fun zip (src: String?, dest: String?, isCreateDir: Boolean, passwd: String?): String? {
        val srcFile = File(src)
        val Dest = buildDestinationZipFilePath(srcFile,dest)
        val parameters = ZipParameters()
        parameters.compressionMethod = Zip4jConstants.COMP_DEFLATE
        parameters.compressionLevel = Zip4jConstants.DEFLATE_LEVEL_NORMAL
        if (!passwd!!.isEmpty()){
            parameters.isEncryptFiles = true
            parameters.encryptionMethod = Zip4jConstants.ENC_METHOD_STANDARD
            parameters.password = passwd.toCharArray()
        }
        try {
            val zipFile = ZipFile(Dest)
            if (srcFile.isDirectory){
                if (!isCreateDir){
                    val subFiles = srcFile.listFiles()
                    val temp = ArrayList<File>()
                    temp.addAll(subFiles)
                    zipFile.addFiles(temp,parameters)
                    return Dest
                }
                zipFile.addFolder(srcFile,parameters)
            }else{
                zipFile.addFile(srcFile,parameters)
            }
            return Dest
        }catch (e: ZipException){
            e.printStackTrace()
        }
        return null
    }



    /**
     * @param srcFile source file
     * @param destParam the destination directory of compressed file
     * @return the real directory for the compressed file
     */



    private fun buildDestinationZipFilePath(srcFile: File, destParam: String?): String? {
        var destparam : String? = destParam
        if (StringUtils.isEmpty(destParam)) {
            destparam = if (srcFile.isDirectory) {
                srcFile.parent + File.separator + srcFile.name + ".zip"
            } else {
                val fileName = srcFile.name.substring(0, srcFile.name.lastIndexOf("."))
                srcFile.parent + File.separator + fileName + ".zip"
            }
        } else {
            if (destParam != null) {
                createDestDirectoryIfNecessary(destParam)
            }
            if (destParam!!.endsWith(File.separator)) {
                val fileName : String = if (srcFile.isDirectory) {
                    srcFile.name
                } else {
                    srcFile.name.substring(0, srcFile.name.lastIndexOf("."))
                }
                destparam += "$fileName.zip"
            }
        }
        return destparam
    }

    /**
     * create the destination directory if needed
     * @param destParam the destination directory
     */

    private fun createDestDirectoryIfNecessary(destParam : String){
        val destDir : File = if (destParam.endsWith(File.separator)){
            File(destParam)
        }else{
            File(destParam.substring(0,destParam.lastIndexOf(File.separator)))
        }
        if (!destDir.exists()){
            destDir.mkdirs()
        }
    }
}