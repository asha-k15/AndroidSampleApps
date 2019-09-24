package provider;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import data.Dog;
import data.DogDAO;
import data.SampleDatabase;


/**
     * A {@link ContentProvider} based on a Room database.
     *
     * <p>Note that you don't need to implement a ContentProvider unless you want to expose the data
     * outside your process or your application already uses a ContentProvider.</p>
     */
    public class SampleContentProvider extends ContentProvider {

        /** The authority of this content provider. */
        public static final String AUTHORITY = "com.example.android.contentprovidersample.provider";

        /** The URI for the Dog table. */
        public static final Uri URI_DOG = Uri.parse(
                "content://" + AUTHORITY + "/" + Dog.TABLE_NAME);

        /** The match code for some items in the Dog table. */
        private static final int CODE_DOG_DIR = 1;

        /** The match code for an item in the Dog table. */
        private static final int CODE_DOG_ITEM = 2;

        /** The URI matcher. */
        private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        static {
            MATCHER.addURI(AUTHORITY, Dog.TABLE_NAME, CODE_DOG_DIR);
            MATCHER.addURI(AUTHORITY, Dog.TABLE_NAME + "/*", CODE_DOG_ITEM);
        }

        @Override
        public boolean onCreate() {
            return true;
        }

        @Nullable
        @Override
        public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                            @Nullable String[] selectionArgs, @Nullable String sortOrder) {
            final int code = MATCHER.match(uri);
            if (code == CODE_DOG_DIR || code == CODE_DOG_ITEM) {
                final Context context = getContext();
                if (context == null) {
                    return null;
                }
                DogDAO dog = SampleDatabase.getInstance(context).dog();
                final Cursor cursor;
                if (code == CODE_DOG_DIR) {
                    cursor = dog.selectAll();
                } else {
                    cursor = dog.selectById(ContentUris.parseId(uri));
                }
                cursor.setNotificationUri(context.getContentResolver(), uri);
                return cursor;
            } else {
                throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }

        @Nullable
        @Override
        public String getType(@NonNull Uri uri) {
            switch (MATCHER.match(uri)) {
                case CODE_DOG_DIR:
                    return "vnd.android.cursor.dir/" + AUTHORITY + "." + Dog.TABLE_NAME;
                case CODE_DOG_ITEM:
                    return "vnd.android.cursor.item/" + AUTHORITY + "." + Dog.TABLE_NAME;
                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }

        @Nullable
        @Override
        public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
            switch (MATCHER.match(uri)) {
                case CODE_DOG_DIR:
                    final Context context = getContext();
                    if (context == null) {
                        return null;
                    }
                    final long id = SampleDatabase.getInstance(context).dog()
                            .insert(Dog.fromContentValues(values));
                    context.getContentResolver().notifyChange(uri, null);
                    return ContentUris.withAppendedId(uri, id);
                case CODE_DOG_ITEM:
                    throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }

        @Override
        public int delete(@NonNull Uri uri, @Nullable String selection,
                          @Nullable String[] selectionArgs) {
            switch (MATCHER.match(uri)) {
                case CODE_DOG_DIR:
                    throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
                case CODE_DOG_ITEM:
                    final Context context = getContext();
                    if (context == null) {
                        return 0;
                    }
                    final int count = SampleDatabase.getInstance(context).dog()
                            .deleteById(ContentUris.parseId(uri));
                    context.getContentResolver().notifyChange(uri, null);
                    return count;
                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }

        @Override
        public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                          @Nullable String[] selectionArgs) {
            switch (MATCHER.match(uri)) {
                case CODE_DOG_DIR:
                    throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
                case CODE_DOG_ITEM:
                    final Context context = getContext();
                    if (context == null) {
                        return 0;
                    }
                    final Dog dog = Dog.fromContentValues(values);
                    dog.id = ContentUris.parseId(uri);
                    final int count = SampleDatabase.getInstance(context).dog()
                            .update(dog);
                    context.getContentResolver().notifyChange(uri, null);
                    return count;
                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }

        @SuppressWarnings("RedundantThrows") /* This gets propagated up from the Callable */
        @NonNull
        @Override
        public ContentProviderResult[] applyBatch(
                @NonNull final ArrayList<ContentProviderOperation> operations)
                throws OperationApplicationException {
            final Context context = getContext();
            if (context == null) {
                return new ContentProviderResult[0];
            }
            final SampleDatabase database = SampleDatabase.getInstance(context);
            return database.runInTransaction(new Callable<ContentProviderResult[]>() {
                @Override
                public ContentProviderResult[] call() throws OperationApplicationException {
                    return SampleContentProvider.super.applyBatch(operations);
                }
            });
        }

        @Override
        public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] valuesArray) {
            switch (MATCHER.match(uri)) {
                case CODE_DOG_DIR:
                    final Context context = getContext();
                    if (context == null) {
                        return 0;
                    }
                    final SampleDatabase database = SampleDatabase.getInstance(context);
                    final Dog[] dogs = new Dog[valuesArray.length];
                    for (int i = 0; i < valuesArray.length; i++) {
                        dogs[i] = Dog.fromContentValues(valuesArray[i]);
                    }
                    return database.dog().insertAll(dogs).length;
                case CODE_DOG_ITEM:
                    throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);
                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
}
