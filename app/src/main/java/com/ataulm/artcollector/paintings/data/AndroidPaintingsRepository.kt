package com.ataulm.artcollector.paintings.data

import com.ataulm.artcollector.ApiRecord
import com.ataulm.artcollector.HarvardArtMuseumApi
import com.ataulm.artcollector.paintings.domain.Painting
import com.ataulm.artcollector.paintings.domain.PaintingsRepository
import javax.inject.Inject

internal class AndroidPaintingsRepository @Inject constructor(
        private val harvardArtMuseumApi: HarvardArtMuseumApi
) : PaintingsRepository {

    override suspend fun paintings(): List<Painting> {
        return harvardArtMuseumApi.paintings().await()
                .records.map { it.toPainting() }
    }

    private fun ApiRecord.toPainting(): Painting {
        return Painting(
                id.toString(),
                title,
                description,
                primaryImageUrl ?: "wtf" // this is in the JSON so why is it null in ApiRecord?
        )
    }
}