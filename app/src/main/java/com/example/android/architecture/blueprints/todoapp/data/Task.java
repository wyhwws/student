/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksPersistenceContract;
import com.google.common.base.Objects;
import com.google.common.base.Strings;

import java.util.UUID;

/**
 * Immutable model class for a Task.
 */
public final class Task {

    @NonNull
    private final String mId;

    @Nullable
    private final String mTitle;

    @Nullable
    private final String mHistory;
    @Nullable
    private final String mDescription;
    @Nullable
    private final String mImageUrl;

    private final boolean mCompleted;

    private int mInternalId;

    /**
     * Use this constructor to create a new active Task.
     *
     * @param title
     * @param description
     */
    public Task(@Nullable String title, @Nullable String history, @Nullable String description, String imageUrl) {
        mId = UUID.randomUUID().toString();
        mTitle = title;
        mHistory = history;
        mDescription = description;
        mImageUrl = imageUrl;
        mCompleted = false;
    }

    /**
     * Use this constructor to create an active Task if the Task already has an id (copy of another
     * Task).
     *
     * @param title
     * @param description
     * @param id of the class
     */
    public Task(@Nullable String title, @Nullable String history, @Nullable String description, String imageUrl, String id) {
        mId = id;
        mTitle = title;
        mHistory = history;
        mDescription = description;
        mImageUrl = imageUrl;
        mCompleted = false;
    }

    /**
     * Use this constructor to create a new completed Task.
     *
     * @param title
     * @param description
     * @param completed
     */
    public Task(@Nullable String title, @Nullable String history, @Nullable String description, String imageUrl, boolean completed) {
        mId = UUID.randomUUID().toString();
        mTitle = title;
        mHistory = history;
        mDescription = description;
        mImageUrl = imageUrl;
        mCompleted = completed;
    }

    /**
     * Use this constructor to specify a completed Task if the Task already has an id (copy of
     * another Task).
     *
     * @param title
     * @param description
     * @param id
     * @param completed
     */
    public Task(@Nullable String title,  @Nullable String history,@Nullable String description,String imageUrl, String id,  boolean completed) {
        mId = id;
        mTitle = title;
        mHistory = history;
        mDescription = description;
        mImageUrl = imageUrl;
        mCompleted = completed;
    }

    /**
     * Use this constructor to return a Task from a Cursor
     *
     * @return
     */
    public static Task from(Cursor cursor) {
        String entryId = cursor.getString(cursor.getColumnIndexOrThrow(
                TasksPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(
                TasksPersistenceContract.TaskEntry.COLUMN_NAME_TITLE));
        String history = cursor.getString(cursor.getColumnIndexOrThrow(
                TasksPersistenceContract.TaskEntry.COLUMN_NAME_HISTORY));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(
                TasksPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION));
        String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(
                TasksPersistenceContract.TaskEntry.COLUMN_NAME_IMAGEURL));
        boolean completed = cursor.getInt(cursor.getColumnIndexOrThrow(
                TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED)) == 1;
        return new Task(title,history, description,imageUrl, entryId, completed);
    }

    public static Task from(ContentValues values) {
        String entryId = values.getAsString(TasksPersistenceContract.TaskEntry.COLUMN_NAME_ENTRY_ID);
        String title = values.getAsString(TasksPersistenceContract.TaskEntry.COLUMN_NAME_TITLE);
        String history = values.getAsString(TasksPersistenceContract.TaskEntry.COLUMN_NAME_HISTORY);
        String description = values.getAsString(TasksPersistenceContract.TaskEntry.COLUMN_NAME_DESCRIPTION);
        String imageUrl = values.getAsString(TasksPersistenceContract.TaskEntry.COLUMN_NAME_IMAGEURL);
        boolean completed = values.getAsInteger(TasksPersistenceContract.TaskEntry.COLUMN_NAME_COMPLETED) == 1;

        return new Task(title,history, description,imageUrl, entryId, completed);
    }

    @NonNull
    public String getId() {
        return mId;
    }

    @Nullable
    public String getTitle() {
        return mTitle;
    }
    public String getHistory() {
        return mHistory;
    }
    @Nullable
    public String getTitleForList() {
        if (!Strings.isNullOrEmpty(mTitle)) {
            return mTitle;
        } else {
            return mDescription;
        }
    }
    public String getHistoryForList() {
        if (!Strings.isNullOrEmpty(mHistory)) {
            return mHistory;
        } else {
            return mDescription;
        }
    }
    @Nullable
    public String getImageForList() {
        if (!Strings.isNullOrEmpty(mImageUrl)) {
            return mImageUrl;
        } else {
            return mDescription;
        }
    }


    @Nullable
    public String getDescription() {
        return mDescription;
    }
    @Nullable
    public String getImageUrl() {
        return mImageUrl;
    }
    public boolean isCompleted() {
        return mCompleted;
    }

    public boolean isActive() {
        return !mCompleted;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(mTitle) &&
               Strings.isNullOrEmpty(mDescription);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equal(mId, task.mId) &&
                Objects.equal(mTitle, task.mTitle) &&Objects.equal(mHistory, task.mHistory) &&
                Objects.equal(mDescription, task.mDescription)
                &&
                Objects.equal(mImageUrl, task.mImageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mTitle,mHistory, mDescription, mImageUrl);
    }
    @Override
    public String toString() {
        return "Task with title " + mTitle;
    }
}
