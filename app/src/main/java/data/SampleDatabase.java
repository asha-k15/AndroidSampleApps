package data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import androidx.annotation.VisibleForTesting;

/**
 * The Room database.
 */
@Database(entities = {Dog.class}, version = 1)
public abstract class SampleDatabase extends RoomDatabase {

    /**
     * @return The DAO for the Dog table.
     */
    @SuppressWarnings("WeakerAccess")
    public abstract DogDAO dog();

    /** The only instance */
    private static SampleDatabase sInstance;

    /**
     * Gets the singleton instance of SampleDatabase.
     *
     * @param context The context.
     * @return The singleton instance of SampleDatabase.
     */
    public static synchronized SampleDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room
                    .databaseBuilder(context.getApplicationContext(), SampleDatabase.class, "ex")
                    .build();
            sInstance.populateInitialData();
        }
        return sInstance;
    }

    /**
     * Switches the internal implementation with an empty in-memory database.
     *
     * @param context The context.
     */
    @VisibleForTesting
    public static void switchToInMemory(Context context) {
        sInstance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                SampleDatabase.class).build();
    }

    /**
     * Inserts the dummy data into the database if it is currently empty.
     */
    private void populateInitialData() {
        if (dog().count() == 0) {
            runInTransaction(new Runnable() {
                @Override
                public void run() {
                    Dog dog = new Dog();
                    for (int i = 0; i < Dog.DOG_BREEDS.length; i++) {
                        dog.name = Dog.DOG_BREEDS[i];
                        dog().insert(dog);
                    }
                }
            });
        }
    }
}
