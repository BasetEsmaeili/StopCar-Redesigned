package ir.esmaeili.stopcar.utils

import com.google.android.gms.maps.model.LatLng

object Constants {
    const val SPLASH_DURATION: Long = 2000
    const val INTRO_PREF_KEY = "intro"
    const val DATABASE_NAME = "stop_car.db"
    const val COLOR_PICK_SPAN_COUNT = 4
    const val LOCATION_PERMISSION_REQUEST_CODE = 2548
    const val LOCATION_DIALOG_REQUEST_CODE = 6874
    const val LOCATION_ZOOM = 18f
    const val GEO_BASE_URL = "https://api.neshan.org/v2/"
    const val SPAN_COUNT_SELECT_CAR = 2
    val TEHRAN_LATLNG = LatLng(
        35.6798689
        , 51.3969006
    )
    const val KEY_MAP_FRAGMENT = "MapFragment"
    const val INIT_MAP_DELAY = 500L
    const val DATE_FORMAT = "j F Y"
    const val VAZIR_FONT_NAME = "vazir"
    const val TANHA_FONT_NAME = "tanha"
    const val PARASTOO_FONT_NAME = "parastoo"
    const val MIKHAK_FONT_NAME = "mikhak"
    const val KEY_TRAFFIC = "trafficMap"
    const val KEY_APP_TYPE_FACE = "fontTypeface"
    const val KEY_DARK_NIGHT = "darkNight"
    const val KEY_LOCALE = "appLocale"
    const val MAP_MARKER_HEIGHT = 64f
    const val MAP_MARKER_WIDTH = 64f
    const val EVENT_SAVE_PARK = 0
    const val EVENT_GET_LOCATION = 1
    const val EVENT_EXPAND_COLOR_PICKER = 2
    const val EVENT_SAVE_NEW_CAR = 6
    const val ITEM_ANIMATOR_INTERPOLATOR = 1f
    const val EVENT_NEXT_ITEM = 7
    const val KEY_BACKUP_CLEAR_DATABASE = "clearDatabase"
    const val KEY_CREDITS = "credits"
    const val NIGHT_MODE_DEFAULT_VALUE = false
    const val DEFAULT_TYPE_FACE_VALUE = "0"
    const val TRAFFIC_DEFAULT_VALUE = true
    const val KEY_CAR_MODEL = "carModel"
    const val KEY_CAR_NAME = "carName"
    const val KEY_IR_CODE = "irCode"
    const val KEY_PART_THREE_CODE = "partThreeCode"
    const val KEY_KEY_WORD = "keyword"
    const val KEY_PART_TWO = "partTwo"
    const val KEY_CAR_COLOR = "carColor"
    const val KEY_HISTORY_LIST = "history"
    const val KEY_PARK_DATE = "parkDate"
    const val KEY_PARK_CLOCK = "parkClock"
    const val KEY_PARK_ADDRESS = "parkAddress"
    const val KEY_PLAQUE_IR_CODE = "irCode"
    const val KEY_PLAQUE_PART_THREE = "partThree"
    const val KEY_PLAQUE_KEYWORD = "keyWOrd"
    const val KEY_PLAQUE_PART_TWO = "partTwo"
    const val KEY_PARK_LATITUDE = "parkLatitude"
    const val KEY_PARK_LONGITUDE = "parkLongitude"
    const val KEY_HISTORY_LIST_SATE = "historyLayoutManager"
    const val VIEW_TYPE_TOOLBAR = 1
    const val VIEW_TYPE_LIST = 2
    const val TODAY_VIEW = "&#8226;"
    const val DELAY_GEO_ADDRESS_REQUEST = 2L
    const val NESHAN_RESULT_SUCCESS = "OK"
    const val ACTION_DOWN_Y_TRANSLATION = -18f
    const val ACTION_UP_Y_TRANSLATION = 2f
    const val KEY_CONTACT_ME = "contactMe"
    const val URL_MY_BLOG = "https://androiddevss.blogspot.com/"
}