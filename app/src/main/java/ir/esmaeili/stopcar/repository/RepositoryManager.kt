package ir.esmaeili.stopcar.repository

import ir.esmaeili.stopcar.repository.database.DBHelper
import ir.esmaeili.stopcar.repository.local.StaticDataHelper
import ir.esmaeili.stopcar.repository.network.ApiHelper
import ir.esmaeili.stopcar.repository.preferences.PreferencesHelper


interface RepositoryManager : StaticDataHelper, DBHelper,
    PreferencesHelper,
    ApiHelper