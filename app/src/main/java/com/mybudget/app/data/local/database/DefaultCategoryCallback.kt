package com.mybudget.app.data.local.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class DefaultCategoryCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.execSQL("INSERT INTO categories (name, iconEmoji, colorHex) VALUES ('Food', '🍜', '#F97316')")
        db.execSQL("INSERT INTO categories (name, iconEmoji, colorHex) VALUES ('Coffee', '☕', '#A16207')")
        db.execSQL("INSERT INTO categories (name, iconEmoji, colorHex) VALUES ('Clothes', '👕', '#EC4899')")
        db.execSQL("INSERT INTO categories (name, iconEmoji, colorHex) VALUES ('Transport', '🚕', '#0EA5E9')")
        db.execSQL("INSERT INTO categories (name, iconEmoji, colorHex) VALUES ('Shopping', '🛍️', '#8B5CF6')")
        db.execSQL("INSERT INTO categories (name, iconEmoji, colorHex) VALUES ('Bills', '🧾', '#64748B')")
        db.execSQL("INSERT INTO categories (name, iconEmoji, colorHex) VALUES ('Health', '💊', '#10B981')")
        db.execSQL("INSERT INTO categories (name, iconEmoji, colorHex) VALUES ('Other', '✨', '#64748B')")
    }
}
