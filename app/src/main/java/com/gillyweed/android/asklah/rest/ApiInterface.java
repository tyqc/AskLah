package com.gillyweed.android.asklah.rest;

import com.gillyweed.android.asklah.data.model.AddComment;
import com.gillyweed.android.asklah.data.model.AddPost;
import com.gillyweed.android.asklah.data.model.Comment;
import com.gillyweed.android.asklah.data.model.EditComment;
import com.gillyweed.android.asklah.data.model.EditPost;
import com.gillyweed.android.asklah.data.model.GetPost;
import com.gillyweed.android.asklah.data.model.SubscriptionPosts;
import com.gillyweed.android.asklah.data.model.SubscriptionTag;
import com.gillyweed.android.asklah.data.model.SubscriptionTags;
import com.gillyweed.android.asklah.data.model.Tag;
import com.gillyweed.android.asklah.data.model.TagArray;
import com.gillyweed.android.asklah.data.model.TagPostList;
import com.gillyweed.android.asklah.data.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Envy 15 on 28/6/2017.
 */

public interface ApiInterface {

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @POST("user/login")
    Call<User> login(
            @Body User userBody,
            @Query("include") String includes);

//    @Headers({
//            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
//            "Content-Type: application/json"})
//    @GET("user")
//    Call<User> getProfile(@Header("Auth_Key") String accessToken, @Query("include") String includes);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @DELETE("user/logout")
    Call<ResponseBody> logout(@Header("Auth_Key") String accessToken);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @PUT("user/update")
    Call<User> update(@Header("Auth_Key") String accessToken,@Body User newUsername);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @GET("user/tags")
    Call<SubscriptionTags> getAllSubscribedTag(@Header("Auth_Key") String accessToken, @Query("nus_id") String nusId);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @POST("tags")
    Call<SubscriptionTag> addNewTag(@Header("Auth_Key") String accessToken, @Body Tag newTag);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @PUT("tags")
    Call<ResponseBody> editTag(@Header("Auth_Key") String accessToken, @Body Tag editTag, @Query("tag_id") int tagId);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @DELETE("user/tags")
    Call<ResponseBody> unsubscribeTag(@Header("Auth_Key") String accessToken, @Query("tag_id") int tagId);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @DELETE("tags")
    Call<ResponseBody> deleteTag(@Header("Auth_Key") String accessToken, @Query("tag_id") int tagId);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @GET("tags")
    Call<TagArray> getTags(@Header("Auth_Key") String accessToken);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @POST("post")
    Call<ResponseBody> addNewPost(@Header("Auth_Key") String accessToken, @Query("nus_id") String nusId, @Body AddPost newPost);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @GET("tags/post")
    Call<TagPostList> getPostList(@Header("Auth_Key") String accessToken, @Query("tag_id") int tagId);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @GET("user/post")
    Call<SubscriptionPosts> getSubscribedPost(@Header("Auth_Key") String accessToken);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @GET("post")
    Call<GetPost> getPostThread(@Header("Auth_Key") String accessToken, @Query("post_id") int postId);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @DELETE("post")
    Call<ResponseBody> deletePost(@Header("Auth_Key") String accessToken, @Query("post_id") int postId, @Query("nus_id") String nusId);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @PUT("post")
    Call<ResponseBody> editPost(@Header("Auth_Key") String accessToken, @Body EditPost editPost);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @POST("reply")
    Call<Comment> commentPost(@Header("Auth_Key") String accessToken, @Body AddComment newComment);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @PUT("reply")
    Call<Comment> editComment(@Header("Auth_Key") String accessToken, @Body EditComment comment);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @DELETE("reply")
    Call<ResponseBody> deleteComment(@Header("Auth_Key") String accessToken, @Query("comment_id") int commentId);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @PUT("post/upvote")
    Call<ResponseBody> upvoteDownvotePost(@Header("Auth_Key") String accessToken, @Query("post_id") int postId);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @PUT("comment/upvote")
    Call<ResponseBody> upvoteDownvoteComment(@Header("Auth_Key") String accessToken, @Query("comment_id") int commentId);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @PUT("comment/pin")
    Call<ResponseBody> pinUnpinAnswer(@Header("Auth_Key") String accessToken, @Query("comment_id") int commentId);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @POST("user/tags")
    Call<Tag> subscribeTag(@Header("Auth_Key") String accessToken, @Query("tag_id") int tagId);


}
