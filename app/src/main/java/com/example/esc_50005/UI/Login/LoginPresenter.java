package com.example.esc_50005.UI.Login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.esc_50005.Database.UsersInformation.UsersInformationDO;
import com.example.esc_50005.Database.UsersInformation.UsersInformationRemoteDataSource;

import java.util.ArrayList;

import static com.google.common.base.Preconditions.*;


public class LoginPresenter implements LoginContract.Presenter  {

    private final LoginContract.View mLoginView;
    public UsersInformationRemoteDataSource mLoginRepository;

    ArrayList<UsersInformationDO> userInformationJsonData;
    ArrayList<UsersInformationDO> userBruteForceJsonData;

    public LoginPresenter(@NonNull UsersInformationRemoteDataSource usersInformationRepository, @NonNull LoginContract.View contractView) {
        mLoginRepository=usersInformationRepository;
        mLoginView = checkNotNull(contractView, "loginView cannot be null!");
        mLoginView.setPresenter(this);
    }
    @Override
    public void start()
    {
//        mLoginView.setUpLogin();
    }

    public void addBruteForceCount(String userId, String fullName)
    {
        userBruteForceJsonData=mLoginRepository.queryAParticularUser(userId);
        if(userBruteForceJsonData.size()!=0)
        {
            int count=Integer.parseInt(userBruteForceJsonData.get(0).getBruteForceCount());

            if(count>2)
            {
                mLoginView.showSecurityQuestion();
            }
            else{
                UsersInformationDO editedUser;
                editedUser=userBruteForceJsonData.get(0);
                count++;
                editedUser.setBruteForceCount(Integer.toString(count));
                mLoginRepository.addUser(editedUser);
//                loadUnsuccessfulLogin();
            }
        }

    }

    @Override
    public void verifySecurityAnswer(String answer, String userId, String fullName) {

        Log.i("answer",answer);
        Log.i("answer",Integer.toString(answer.length()));
        userBruteForceJsonData=mLoginRepository.queryAParticularUser(userId);
        String correctSecurityAnswer=userBruteForceJsonData.get(0).getSecurityAnswer();
        Log.i("correct answer",correctSecurityAnswer);
        Log.i("correct answer",Integer.toString(correctSecurityAnswer.length()));

        if(!correctSecurityAnswer.equals(answer))
        {
            disableAccount();
            mLoginView.showAccountLockedOut();
        }
        else{
            UsersInformationDO editedUser;
            editedUser=userBruteForceJsonData.get(0);
            editedUser.setBruteForceCount(Integer.toString(0));
            editedUser.setDisabled(false);
            mLoginRepository.addUser(editedUser);
            mLoginView.showSuccessfulLogin(userBruteForceJsonData.get(0).getUserId(),userBruteForceJsonData.get(0).getFullName());
        }
    }

    @Override
    public void disableAccount() {

        UsersInformationDO editedUser;
        editedUser=userBruteForceJsonData.get(0);
        editedUser.setDisabled(true);

        mLoginRepository.addUser(editedUser);
//        mLoginView.showAccountLockedOut();
    }

    public void loadUsersFromDatabase(String userId, String userType, String password)
    {

        Log.i("size of it", Integer.toString(userId.length()));
        if(userId.length()==0)
        {
            Log.i("this is null","this is nuk");
            loadUnsuccessfulLogin();
        }
        else if(userId.length()!=0){
            Log.i("this is null","not null");
            userInformationJsonData=mLoginRepository.queryAParticularUser(userId);
            checkIfLoginIsValid(userInformationJsonData, password, userType);
        }

    }

    @Override
    public void loadSuccessfulLogin(String userId, String name) {

        mLoginView.showSuccessfulLogin(userId,name);
    }

    public void checkIfLoginIsValid(ArrayList<UsersInformationDO> userInformationJsonData, String password, String userType){

        Log.i("size",Integer.toString(userInformationJsonData.size()));
        if(userInformationJsonData.size()==0)
        {
            loadUnsuccessfulLogin();
        }

        else{

            if(!userInformationJsonData.get(0).getUserType().equals(userType))
            {
                loadUnsuccessfulLogin();
            }
            else if(userInformationJsonData.get(0).getDisabled().toString().equals("true"))
            {
                loadAccountLockedOut();
            }
            else if(!userInformationJsonData.get(0).getPassword().equals(password))
            {
                loadUnsuccessfulLogin();
            }
            else if(userInformationJsonData.get(0).getPassword().equals(password))
            {
                loadSuccessfulLogin(userInformationJsonData.get(0).getUserId(),userInformationJsonData.get(0).getFullName());
            }
            else{
                addBruteForceCount(userInformationJsonData.get(0).getUserId(),userInformationJsonData.get(0).getFullName());
            }

        }



    }

    @Override
    public void loadSignUp() {
        mLoginView.showSignUp();
    }

    public void loadUnsuccessfulLogin()
    {
        mLoginView.showUnsuccessfulLogin();
    }

    public void loadAccountLockedOut()
    {
        mLoginView.showSecurityQuestion();
    }

}