package data;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import android.database.Cursor;


/**
 * Data access object for Dog.
 */
@Dao
public interface DogDAO {


        /**
         * Counts the number of Dogs in the table.
         *
         * @return The number of Dogs.
         */
        @Query("SELECT COUNT(*) FROM " + Dog.TABLE_NAME)
        int count();

        /**
         * Inserts a Dog into the table.
         *
         * @param Dog A new Dog.
         * @return The row ID of the newly inserted Dog.
         */
        @Insert
        long insert(Dog Dog);

        /**
         * Inserts multiple Dogs into the database
         *
         * @param Dogs An array of new Dogs.
         * @return The row IDs of the newly inserted Dogs.
         */
        @Insert
        long[] insertAll(Dog[] Dogs);

        /**
         * Select all Dogs.
         *
         * @return A {@link Cursor} of all the Dogs in the table.
         */
        @Query("SELECT * FROM " + Dog.TABLE_NAME)
        Cursor selectAll();

        /**
         * Select a Dog by the ID.
         *
         * @param id The row ID.
         * @return A {@link Cursor} of the selected Dog.
         */
        @Query("SELECT * FROM " + Dog.TABLE_NAME + " WHERE " + Dog.COLUMN_ID + " = :id")
        Cursor selectById(long id);

        /**
         * Delete a Dog by the ID.
         *
         * @param id The row ID.
         * @return A number of Dogs deleted. This should always be {@code 1}.
         */
        @Query("DELETE FROM " + Dog.TABLE_NAME + " WHERE " + Dog.COLUMN_ID + " = :id")
        int deleteById(long id);

        /**
         * Update the Dog. The Dog is identified by the row ID.
         *
         * @param Dog The Dog to update.
         * @return A number of Dogs updated. This should always be {@code 1}.
         */
        @Update
        int update(Dog Dog);

    }
