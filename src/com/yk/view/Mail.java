package com.yk.view;

import com.yk.business.BusinessLogic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mail {
    public static void main(String[] args) throws ParseException {
    /*    Batchimport bm=new Batchimport();
        bm.setVisible(true);
        bm.setSize(600,300);
        bm.setLocationRelativeTo(null);
        bm.setDefaultCloseOperation(3);*/

        BusinessLogic bl=new BusinessLogic();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date sdate = sf.parse("2019-02-22 09:51:41");
        Date jdate = sf.parse("2019-02-25 09:51:41");
        bl.vacationTime(sdate,jdate);

        System.out.println(bl.vacationTime(sdate,jdate));

    }
}
