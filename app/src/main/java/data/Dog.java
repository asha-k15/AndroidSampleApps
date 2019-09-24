package data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.content.ContentValues;
import android.provider.BaseColumns;
@Entity
public class Dog {

    /** The name of the Cheese table. */
    public static final String TABLE_NAME = "dog";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    /** The name of the name column. */
    public static final String COLUMN_NAME = "name";

    /** The unique ID of the cheese. */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    public long id;

    /** The name of the cheese. */
    @ColumnInfo(name = COLUMN_NAME)
    public String name;

    /**
     * Create a new {@link Dog} from the specified {@link ContentValues}.
     *
     * @param values A {@link ContentValues} that at least contain {@link #COLUMN_NAME}.
     * @return A newly created {@link Dog} instance.
     */
    public static Dog fromContentValues(ContentValues values) {
        final Dog dog = new Dog();
        if (values.containsKey(COLUMN_ID)) {
            dog.id = values.getAsLong(COLUMN_ID);
        }
        if (values.containsKey(COLUMN_NAME)) {
            dog.name = values.getAsString(COLUMN_NAME);
        }
        return dog;

    }



    /** Dummy data. */
    static final String[] DOG_BREEDS = {
            "Afador", "Affenpinscher", "Afghan Hound", "Airedale Terrier", "Akita", "Alaskan Klee Kai",
            "Alaskan Malamute", "American Bulldog", "American English Coonhound", "American Eskimo Dog",
            "American Foxhound", "American Pit Bull Terrier", "American Pugabull", "American Staffordshire Terrier",
            "American Water Spaniel", "Anatolian Shepherd Dog", "Appenzeller Sennenhunde", "Aussiedoodle",
            "Aussiepom", "Australian Cattle Dog", "Australian Kelpie", "Australian Shepherd", "Australian Terrier",
            "Azawakh", "Barbet", "Basenji", "Bassador", "Basset Hound", "Beagle", "Bearded Collie", "Bedlington Terrier",
            "Belgian Malinois", "Belgian Sheepdog", "Belgian Tervuren", "Berger Picard", "Bernedoodle", "Bernese Mountain Dog",
            "Bichon Frise", "Biewer Terrier", "Black and Tan Coonhound", "Black Mouth Cur", "Black Russian Terrier",
            "Bloodho", "Blue Lacy", "Bluetick Coonhound", "Boerboel", "Bolognese", "Border Terrier", "Borzoi",
            "Boston Terrier", "Bouvier des Flandres", "Boxer", "Boykin Spaniel", "Bracco Italiano", "Briard",
            "Brittany", "Brussels Griffon", "Bull Terrier", "Bullboxer Pit", "Bulldog", "Bullmastiff",
            "Bullmatian", "Cairn Terrier", "Canaan Dog", "Cane Corso", "Cardigan Welsh Corgi", "Catahoula Bulldog",
            "Catahoula Leopard Dog", "Caucasian Shepherd Dog", "Cavachon", "Cavador", "Cavalier King Charles Spaniel",
            "Cavapoo", "Cesky Terrier", "Chabrador", "Cheagle", "Chesapeake Bay Retriever", "Chi-Poo", "Chihuahua",
            "Chinese Crested", "Chinese Shar-Pei", "Chinook", "Chipin", "Chiweenie", "Chow Chow", "Chug", "Chusky",
            "Clumber Spaniel", "Cockapoo", "Cocker Spaniel", "Collie", "Corgi Inu",
            "Corgidor", "Coton de Tulear", "Curly-Coated Retriever", "Dachshund", "Dalmatian", "Dandie Dinmont Terrier",
            "Doberdor", "Doberman Pinscher", "Docker", "Dogo Argentino","Dogue de Bordeaux","Dorgi","Doxiepoo",
            "Doxle","Dutch Shepherd","English Cocker Spaniel","English Foxhound", "English Setter","English Springer Spaniel",
            "English Toy Spaniel","Entlebucher Mountain Dog", "Eurasier","Field Spaniel","Finnish Lapphund",
            "Finnish Spitz","Flat-Coated Retriever","Fox Terrier","French Bulldog", "French Spaniel","German Pinscher",
            "German Shepherd Dog", "German Shorthaired Pointer","German Wirehaired Pointer","Giant Schnauzer",
            "Glen of Imaal Terrier", "Goberian","Goldador","Golden Retriever","Goldendoodle","Great Dane", "Great Pyrenees",
            "Greater Swiss Mountain Dog", "Greyhound","Harrier","Havanese","Hokkaido","Horgi","Ibizan Hound",
            "Icelandic Sheepdog","Irish Red and White Setter","Irish Setter","Irish Terrier","Irish Water Spaniel",
            "Irish Wolfhound","Italian Greyhound",  "Jack Chi", "Jack Russell Terrier","Japanese Chin","Japanese Spitz",
            "Korean Jindo Dog","Karelian Bear Dog","Keeshond","Kerry Blue Terrier","Komondor","Kooikerhondje",
            "Kuvasz","Kyi-Leo","Labernese","Labmaraner", "Labrabull","Labradane","Labradoodle", "Labrador Retriever",
            "Labsky","Lagotto Romagnolo","Lakeland Terrier","ancashire Heeler","Leonberger", "Lhasa Apso",
            "Lowchen","Maltese","Maltese Shih Tzu","Maltipoo", "Manchester Terrier", "Mastiff","Miniature Pinscher",
            "Miniature Schnauzer","Morkie","Mudi","Mutt","Neapolitan Mastiff","Newfoundland","Norfolk Terrier",
            "Norwegian Buhund", "Norwegian Elkhound","Norwegian Lundehund","Norwich Terrier","Nova Scotia Duck Tolling Retriever",
            "Old English Sheepdog", "Otterhound","Papillon","Peekapoo","Pekingese", "Pembroke Welsh Corgi",
            "Petit Basset Griffon Vendeen", "Pharaoh Hound","Pitsky","Plott","Pocket Beagle","Pointer", "Polish Lowland Sheepdog",
            "Pomapoo", "Pomchi","Pomeagle","Pomeranian","Pomsky","Poochon","Poodle", "Portuguese Water Dog",
            "Pug","Puggle","Puli","Pyrenean Shepherd","Rat Terrier","Redbone Coonhound","Rhodesian Ridgeback",
            "Rottweiler","Saint Berdoodle", "Saint Bernard","Saluki", "Samoyed","Schipperke","Schnoodle",
            "Scottish Deerhound","Scottish Terrier","Sealyham Terrier", "Shepsky", "Shetland Sheepdog", "Shiba Inu",
            "Shih-Poo","Shih Tzu", "Shorkie","Siberian Husky", "Silken Windhound","Silky Terrier","Skye Terrier","Sloughi",
            "Small Munsterlander Pointer","Soft Coated Wheaten Terrier", "Stabyhoun", "Staffordshire Bull Terrier","Standard Schnauzer",
            "Sussex Spaniel","Swedish Vallhund","Tibetan Mastiff","Tibetan Spaniel", "Tibetan Terrier","Toy Fox Terrier",
            "Treeing Tennessee Brindle","Treeing Walker Coonhound","Vizsla", "Weimaraner", "Welsh Springer Spaniel",
            "Welsh Terrier","West Highland White Terrier","Whippet","Whoodle","Wirehaired Pointing Griffon",
            "Xoloitzcuintli", "Yorkipoo", "Yorkshire Terrier"
       };

}
