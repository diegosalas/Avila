package com.vectormobile.fundacionavila.listener


import com.vectormobile.fundacionavila.models.MonumentPhotos



interface RecyclerMonumentPhotoListener {
    fun onClick(monumentPhoto: MonumentPhotos, position: Int)

}