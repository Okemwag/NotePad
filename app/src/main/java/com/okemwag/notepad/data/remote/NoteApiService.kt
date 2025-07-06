package com.okemwag.notepad.data.remote

import retrofit2.Response
import retrofit2.http.*

interface NoteApiService {

    @GET("notes")
    suspend fun getAllNotes(
        @Header("Authorization") token: String
    ): Response<List<NoteDto>>

    @GET("notes/{id}")
    suspend fun getNoteById(
        @Header("Authorization") token: String, @Path("id") id: String
    ): Response<NoteDto>

    @POST("notes")
    suspend fun createNote(
        @Header("Authorization") token: String, @Body note: CreateNoteRequest
    ): Response<NoteDto>

    @PUT("notes/{id}")
    suspend fun updateNote(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body note: UpdateNoteRequest
    ): Response<NoteDto>

    @DELETE("notes/{id}")
    suspend fun deleteNote(
        @Header("Authorization") token: String, @Path("id") id: String
    ): Response<Unit>

    @POST("notes/sync")
    suspend fun syncNotes(
        @Header("Authorization") token: String, @Body syncRequest: SyncRequest
    ): Response<SyncResponse>

    @GET("notes/search")
    suspend fun searchNotes(
        @Header("Authorization") token: String,
        @Query("q") query: String,
        @Query("category") category: String? = null
    ): Response<List<NoteDto>>
}