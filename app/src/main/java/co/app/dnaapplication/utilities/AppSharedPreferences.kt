package co.app.dnaapplication.utilities

import co.app.dnaapplication.utilities.AppSettingsCore.getBoolean
import co.app.dnaapplication.utilities.AppSettingsCore.removePrefData
import co.app.dnaapplication.utilities.AppSettingsCore.setBoolean


class AppSharedPreferences{
    companion object {
        private val KEY_APP_IS_OPEN = "app_is_open"
        private val KEY_IS_LOGIN = "is_login"
        private val KEY_IS_PRO_COMPLETE = "is_pro_complete"
        private val KEY_FIREBASE_INSTANCE_ID = "firebase_id"
        private val KEY_USER_ID = "user_id"
        private val KEY_EMAIL_ID = "email_id"
        private val KEY_LOGIN_OBJ = "login_obj"
        private val KEY_PROFILE_OBJ = "profile_obj"
        private val KEY_NOTI_COUNT = "noti_count"
        private val KEY_USER_NAME = "user_name"
        private val BOOKING_ID = "booking_id"
        private val GOT_IT = "GOT_IT"
        private val KEY_LANGUAGE = "selected_language_for_translation"
        private val KEY_USER_DETAILS = "KEY_USER_DETAILS"
        private val USER_TYPE = "USER_TYPE"
        private val FIRST_TIME = "first_time"
        private val CITY_NAME = "city_name"
        private val CITY_ID = "city_id"
        private val CAT_ID = "cat_id"
        private val SUB_CAT_ID = "sub_cat_id"
        private val IsCitySelected = "isCitySelected"
        private val KEY_DEFAULT_ADDRESS_ID = "default_address_id"




        fun clearUserData() {
            removePrefData()
        }

        var appIsOpen: Boolean
            get() = getBoolean(KEY_APP_IS_OPEN, false)
            set(appIsOpen) {
                setBoolean(KEY_APP_IS_OPEN, appIsOpen)
            }

        var isLogin: Boolean
            get() = getBoolean(KEY_IS_LOGIN, false)
            set(value) {
                setBoolean(KEY_IS_LOGIN, value)
            }

        var isProfileComplete: Boolean
            get() = getBoolean(KEY_IS_PRO_COMPLETE, false)
            set(value) {
                setBoolean(KEY_IS_PRO_COMPLETE, value)
            }




        /*var addressId: String
            get() = getString(KEY_DEFAULT_ADDRESS_ID, "") ?: ""
            set(value) {
                setString(KEY_DEFAULT_ADDRESS_ID, value)
            }*/
    }
}