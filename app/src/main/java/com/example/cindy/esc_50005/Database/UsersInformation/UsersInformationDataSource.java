package com.example.cindy.esc_50005.Database.UsersInformation;

import com.example.cindy.esc_50005.Database.FAQ.Faq;

import java.util.ArrayList;

/**
 * Created by cindy on 13/3/2018.
 */

public interface UsersInformationDataSource {

    void removeUser(UsersInformationDO faq);
    void addUser(UsersInformationDO faq);
    ArrayList<UsersInformationDO> queryUser(String username, String password, String userType);

}
