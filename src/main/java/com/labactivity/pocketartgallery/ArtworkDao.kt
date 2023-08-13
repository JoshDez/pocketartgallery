package com.labactivity.pocketartgallery
import androidx.room.*

@Dao
interface ArtworkDao {
    //Retrieve all of reservation_table
    @Query("SELECT * FROM art_table ORDER BY date DESC")
    suspend fun getAllArtwork(): List<Artwork>

    //Retrieve artwork
    @Query("SELECT art FROM art_table WHERE id = :artworkId")
    suspend fun getArtworkById(artworkId: Long): ByteArray?

    //Insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertArtwork(Artwork: Artwork)

    //Delete
    @Query("DELETE FROM art_table WHERE id = :artworkId")
    suspend fun deleteArtworkById(artworkId: Long)
}