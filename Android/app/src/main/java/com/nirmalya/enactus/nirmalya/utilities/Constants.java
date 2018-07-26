package com.nirmalya.enactus.nirmalya.utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.nirmalya.enactus.nirmalya.R;
import com.nirmalya.enactus.nirmalya.model.KnowTeamItem;
import com.nirmalya.enactus.nirmalya.model.ServiceItem;
import com.nirmalya.enactus.nirmalya.model.User;

import java.util.ArrayList;

public class Constants {

    public static String SHARED_PREFS = "nirmalyaPreferences";
    public static String SHARED_PREFS_NAME = "name";
    public static String SHARED_PREFS_EMAIL = "email";
    public static String SHARED_PREFS_ADDRESS = "address";
    public static String SHARED_PREFS_PHONE_NUMBER = "phoneNumber";
    public static String SHARED_PREFS_ACCOUNT_TYPE = "accountType";
    public static String SHARED_PREFS_IS_VISIT_REQUESTED = "isVisitRequested";
    public static String SHARED_PREFS_IS_PREMIUM_USER = "isPremiumUser";
    public static String SHARED_PREFS_IS_VISIT_DONE = "isVisitDone";
    public static String SHARED_PREFS_IS_PROPOSAL_SELECTED = "isProposalSelected";
    public static String SHARED_PREFS_IS_ORDER_MANUFACTURED = "isOrderManufactured";
    public static String SHARED_PREFS_HAS_TRAINER_VISITED = "hasTrainerVisited";
    public static String SHARED_PREFS_IS_INSTALLATION_DONE = "isInstallationDone";
    public static String SHARED_PREFS_FCM_TOKEN = "fcmToken";

    public static String fcmToken = "";
    public static boolean isFcmTokenChanged = false;

    public static String INCOMPLETE_STATUS_STRING = "Incomplete";
    public static String COMPLETE_STATUS_STRING = "Complete";
    public static String WE_WILL_VISIT_SOON_STRING = "We will visit you soon";
    public static String WORKING_ON_IT_STRING = "We're still working on it!";
    public static String VISIT_NOT_REQUESTED_STRING = "You haven't requested a visit yet!";
    public static String PAYMENT_NOT_DONE = "We haven't received the payment yet!";
    public static String PRESS_CONTINUE_TO_GO_TO_NEXT_STEP = "Complete! Press continue to view the next step!";


    /*
    This variable is used as an in-memory store for the
    properties of the current user. It is refreshed every time
    the services activity is started.
     */
    public static User currentUser;


    /* This list is private because no one should be able to access it directly.
    The list should only be accessible through a constructor.
     */
    private static ArrayList<ServiceItem> serviceItems;
    //Create the serviceItems list and return it
    public static ArrayList<ServiceItem> getServiceItems() {
        /* Before adding items to the list, check if it is null.
        Otherwise each time this list is accessed, these items will get added to it.
         */
        if (serviceItems == null) {
            serviceItems = new ArrayList<>();
            serviceItems.add(new ServiceItem("Our Products", "Come see our offerings"));
            serviceItems.add(new ServiceItem("Our Impact", "Know our impact on the world"));
            serviceItems.add(new ServiceItem("Our Resources", "Learn about composting"));
            serviceItems.add(new ServiceItem("Our Team", "Get contact information for our team"));
            serviceItems.add(new ServiceItem("Orders", "Place an order and track its progress"));
        }
        return serviceItems;
    }

    private static ArrayList<Integer> serviceItemDrawableIds;

    public static ArrayList<Integer> getServiceItemDrawableIds() {
        /* Before adding items to the list, check if it is null.
        Otherwise each time this list is accessed, these items will get added to it.
         */
        if (serviceItemDrawableIds == null) {
            serviceItemDrawableIds = new ArrayList<>();
            serviceItemDrawableIds.add(R.drawable.our_products);
            serviceItemDrawableIds.add(R.drawable.our_impact);
            serviceItemDrawableIds.add(R.drawable.our_resources);
            serviceItemDrawableIds.add(R.drawable.our_team);
            serviceItemDrawableIds.add(R.drawable.orders);
        }

        return serviceItemDrawableIds;
    }
    private static ArrayList<KnowTeamItem> teamdetails;
    // Create the teamdetails list and return it
    public  static ArrayList<KnowTeamItem> getTeamMemberDetails(){
        /* Before adding items to the list, check if it is null.
        Otherwise each time this list is accessed, these items will get added to it.
         */
        if(teamdetails== null){
            teamdetails = new ArrayList<>();
            teamdetails.add(new KnowTeamItem("CEO", "Kiran Jain", "8376026716", "kjain3372@gmail.com", R.drawable.kiran_jain));
            teamdetails.add(new KnowTeamItem("CEO", "Sankalp Katiyar", "9582059505","sanklapkatiyar.com@gmail.com",R.drawable.sanklap_katiyar));
            teamdetails.add(new KnowTeamItem("Project Leader","Sahil Vaish","8860753261","svaish758@gmail.com",R.drawable.sahil_vaish));
            teamdetails.add(new KnowTeamItem("Project Leader","Aditi Gupta","8826093285","aditigupta802@gmail.com",R.drawable.aditi_gupta));
            teamdetails.add(new KnowTeamItem("Project Leader","Naman Bhargava","8130968478","namanbhargava18@gmail.com",R.drawable.naman_bhargava));
            teamdetails.add(new KnowTeamItem("Project Executive","Somendra Singh Jadon","8349044800","somendra07jadon@gmail.com",R.drawable.somendra_singh_jadon));
            teamdetails.add(new KnowTeamItem("Project Executive","Ajay Agarwal","8107920251","ajayagrawal28598@gmail.com",R.drawable.ajay_agrawal));
            teamdetails.add(new KnowTeamItem("Project Executive","Sahil Dahake","9172264974","sahildahake@gmail.com",R.drawable.sahil_dahake));
            teamdetails.add(new KnowTeamItem("Project Executive","Piyush Kansal","8708641376","piyush.logics@gmail.com",R.drawable.piyush_kansal));
            teamdetails.add(new KnowTeamItem("Project Executive","Rushil Gupta","7973204443","rushilgupta26051999@gmail.com",R.drawable.rushil_gupta));
            teamdetails.add(new KnowTeamItem("Project Executive","Sakshi Gupta","9987241654","sakshigupta1771@gmail.com",R.drawable.sakshi_gupta));
            teamdetails.add(new KnowTeamItem("Project Executive","Rahul Mehta","8824633771","rahulbushanmehta@gmail.com",R.drawable.rahul_mehta));
        }
        return  teamdetails;
    }

    private static ArrayList<Drawable> teamImages;

    public static ArrayList<Drawable> getTeamImages(Context context) {

        if (teamImages == null) {
            teamImages = new ArrayList<>();
            teamImages.add(ContextCompat.getDrawable(context, R.drawable.kiran_jain));
            teamImages.add(ContextCompat.getDrawable(context, R.drawable.sanklap_katiyar));
            teamImages.add(ContextCompat.getDrawable(context, R.drawable.sahil_vaish));
            teamImages.add(ContextCompat.getDrawable(context, R.drawable.aditi_gupta));
            teamImages.add(ContextCompat.getDrawable(context, R.drawable.naman_bhargava));
            teamImages.add(ContextCompat.getDrawable(context, R.drawable.somendra_singh_jadon));
            teamImages.add(ContextCompat.getDrawable(context, R.drawable.ajay_agrawal));
            teamImages.add(ContextCompat.getDrawable(context, R.drawable.sahil_dahake));
            teamImages.add(ContextCompat.getDrawable(context, R.drawable.piyush_kansal));
            teamImages.add(ContextCompat.getDrawable(context, R.drawable.rushil_gupta));
            teamImages.add(ContextCompat.getDrawable(context, R.drawable.sakshi_gupta));
            teamImages.add(ContextCompat.getDrawable(context, R.drawable.rahul_mehta));
        }

        return teamImages;
    }
}
