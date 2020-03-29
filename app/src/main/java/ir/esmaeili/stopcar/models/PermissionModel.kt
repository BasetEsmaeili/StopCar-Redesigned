package ir.esmaeili.stopcar.models

data class PermissionModel(
    val requestCode: Int,
    val permissions: List<String>,
    val grantResults: List<Int>
) {
}