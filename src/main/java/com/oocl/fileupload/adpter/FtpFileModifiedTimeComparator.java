package com.oocl.fileupload.adpter;

import org.apache.commons.net.ftp.FTPFile;

import java.util.Comparator;

public class FtpFileModifiedTimeComparator implements Comparator<FTPFile> {
    @Override
    public int compare(FTPFile o1, FTPFile o2) {
        return o1.getTimestamp().compareTo(o2.getTimestamp());
    }
}
