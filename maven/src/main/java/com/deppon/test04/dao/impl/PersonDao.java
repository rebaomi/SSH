package com.deppon.test04.dao.impl;

import com.deppon.test04.dao.IPersonDao;

public class PersonDao implements IPersonDao{
    @Override
    public void save(){
    	System.out.println("------------from PersonDao.save()");
    }


}
