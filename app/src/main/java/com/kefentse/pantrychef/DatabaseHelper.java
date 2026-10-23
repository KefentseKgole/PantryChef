package com.kefentse.pantrychef;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

// Constructor for the SQLite database
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pantrychef.db";
    private static final int DATABASE_VERSION = 2; // Upgraded to version 2!

    // Pantry table
    public static final String TABLE_PANTRY = "pantry_items";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_UNIT = "unit";
    public static final String COL_EXPIRY = "expiry_date";

    // Recipe table
    public static final String TABLE_RECIPES = "recipes";
    public static final String COL_RECIPE_ID = "recipe_id";
    public static final String COL_RECIPE_NAME = "recipe_name";
    public static final String COL_INSTRUCTIONS = "instructions";

    // Recipe Ingredients table
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    public static final String COL_ING_ID = "ing_id";
    public static final String COL_ING_RECIPE_ID = "recipe_id";
    public static final String COL_ING_NAME = "ingredient_name";
    public static final String COL_ING_QTY = "required_quantity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Pantry Table
        db.execSQL("CREATE TABLE " + TABLE_PANTRY + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT NOT NULL, " +
                COL_QUANTITY + " REAL NOT NULL, " +
                COL_UNIT + " TEXT, " +
                COL_EXPIRY + " TEXT)");

        // Create Recipes Table
        db.execSQL("CREATE TABLE " + TABLE_RECIPES + " (" +
                COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECIPE_NAME + " TEXT NOT NULL, " +
                COL_INSTRUCTIONS + " TEXT NOT NULL)");

        // Create Recipe Ingredients Table
        db.execSQL("CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                COL_ING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ING_RECIPE_ID + " INTEGER NOT NULL, " +
                COL_ING_NAME + " TEXT NOT NULL, " +
                COL_ING_QTY + " REAL NOT NULL)");

        seedRecipes(db); // Pre-load the recipes
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);
        onCreate(db);
    }

    // ---------- PANTRY CRUD (Unchanged) ----------
    public long addPantryItem(PantryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, item.getName());
        values.put(COL_QUANTITY, item.getQuantity());
        values.put(COL_UNIT, item.getUnit());
        values.put(COL_EXPIRY, item.getExpiryDate());
        long newId = db.insert(TABLE_PANTRY, null, values);
        db.close();
        return newId;
    }

    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PANTRY, null, null, null, null, null, COL_NAME + " ASC");
        if (cursor.moveToFirst()) {
            do {
                PantryItem item = new PantryItem();
                item.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COL_ID)));
                item.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
                item.setQuantity(cursor.getDouble(cursor.getColumnIndexOrThrow(COL_QUANTITY)));
                item.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(COL_UNIT)));
                item.setExpiryDate(cursor.getString(cursor.getColumnIndexOrThrow(COL_EXPIRY)));
                items.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return items;
    }

    public int updatePantryItem(PantryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, item.getName());
        values.put(COL_QUANTITY, item.getQuantity());
        values.put(COL_UNIT, item.getUnit());
        values.put(COL_EXPIRY, item.getExpiryDate());
        int rows = db.update(TABLE_PANTRY, values, COL_ID + " = ?", new String[]{String.valueOf(item.getId())});
        db.close();
        return rows;
    }

    public void deletePantryItem(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PANTRY, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // ---------- SEED RECIPES ----------
    private void seedRecipes(SQLiteDatabase db) {
        // Recipe 1: Omelette
        long r1 = insertRecipe(db, "Cheese Omelette", "1. Beat eggs. 2. Melt butter in pan. 3. Cook eggs. 4. Add cheese.");
        insertIngredient(db, r1, "eggs", 2);
        insertIngredient(db, r1, "butter", 1);
        insertIngredient(db, r1, "cheese", 1);

        // Recipe 2: Tomato Pasta
        long r2 = insertRecipe(db, "Tomato Pasta", "1. Boil pasta. 2. Fry tomatoes and garlic in oil. 3. Mix together.");
        insertIngredient(db, r2, "pasta", 1);
        insertIngredient(db, r2, "tomatoes", 2);
        insertIngredient(db, r2, "garlic", 1);
        insertIngredient(db, r2, "oil", 1);

        // Recipe 3: Simple Salad
        long r3 = insertRecipe(db, "Simple Salad", "1. Chop lettuce and tomatoes. 2. Mix in a bowl with dressing.");
        insertIngredient(db, r3, "lettuce", 1);
        insertIngredient(db, r3, "tomatoes", 1);
        insertIngredient(db, r3, "dressing", 1);

        // Recipe 4: Toast with Jam
        long r4 = insertRecipe(db, "Toast with Jam", "1. Toast bread. 2. Spread butter and jam on top.");
        insertIngredient(db, r4, "bread", 2);
        insertIngredient(db, r4, "jam", 1);
        insertIngredient(db, r4, "butter", 1);

        // Recipe 5: Chicken Stir-fry
        long r5 = insertRecipe(db, "Chicken Stir-fry", "1. Cut chicken. 2. Fry with veggies and soy sauce.");
        insertIngredient(db, r5, "chicken", 1);
        insertIngredient(db, r5, "vegetables", 2);
        insertIngredient(db, r5, "soy sauce", 1);

        // Recipe 6: Pancakes
        long r6 = insertRecipe(db, "Pancakes", "1. Mix flour, milk, and eggs. 2. Fry in pan.");
        insertIngredient(db, r6, "flour", 1);
        insertIngredient(db, r6, "milk", 1);
        insertIngredient(db, r6, "eggs", 1);

        // Recipe 7: Grilled Cheese
        long r7 = insertRecipe(db, "Grilled Cheese", "1. Butter bread. 2. Add cheese. 3. Grill until golden.");
        insertIngredient(db, r7, "bread", 2);
        insertIngredient(db, r7, "cheese", 2);
        insertIngredient(db, r7, "butter", 1);

        // Recipe 8: Tuna Sandwich
        long r8 = insertRecipe(db, "Tuna Sandwich", "1. Mix tuna with mayo. 2. Put on bread.");
        insertIngredient(db, r8, "tuna", 1);
        insertIngredient(db, r8, "mayo", 1);
        insertIngredient(db, r8, "bread", 2);

        // Recipe 9: Rice and Beans
        long r9 = insertRecipe(db, "Rice and Beans", "1. Boil rice. 2. Heat beans. 3. Mix.");
        insertIngredient(db, r9, "rice", 1);
        insertIngredient(db, r9, "beans", 1);

        // Recipe 10: Fruit Smoothie
        long r10 = insertRecipe(db, "Fruit Smoothie", "1. Blend fruit, yogurt, and milk.");
        insertIngredient(db, r10, "fruit", 2);
        insertIngredient(db, r10, "yogurt", 1);
        insertIngredient(db, r10, "milk", 1);

        // Recipe 11: Garlic Butter Noodles
        long r11 = insertRecipe(db, "Garlic Butter Noodles", "1. Boil noodles. 2. Toss with garlic and butter.");
        insertIngredient(db, r11, "noodles", 1);
        insertIngredient(db, r11, "garlic", 1);
        insertIngredient(db, r11, "butter", 1);

        // Recipe 12: Avocado Toast
        long r12 = insertRecipe(db, "Avocado Toast", "1. Toast bread. 2. Mash avocado on top.");
        insertIngredient(db, r12, "bread", 1);
        insertIngredient(db, r12, "avocado", 1);

        // Recipe 13: Veggie Omelette
        long r13 = insertRecipe(db, "Veggie Omelette", "1. Beat eggs. 2. Fry with chopped vegetables.");
        insertIngredient(db, r13, "eggs", 2);
        insertIngredient(db, r13, "vegetables", 1);

        // Recipe 14: Quick Pizza
        long r14 = insertRecipe(db, "Quick Pizza", "1. Put tomato sauce on bread. 2. Add cheese. 3. Bake.");
        insertIngredient(db, r14, "bread", 1);
        insertIngredient(db, r14, "tomato sauce", 1);
        insertIngredient(db, r14, "cheese", 1);

        // Recipe 15: Cereal
        long r15 = insertRecipe(db, "Cereal", "1. Put cereal in bowl. 2. Add milk.");
        insertIngredient(db, r15, "cereal", 1);
        insertIngredient(db, r15, "milk", 1);
    }

    private long insertRecipe(SQLiteDatabase db, String name, String instructions) {
        ContentValues values = new ContentValues();
        values.put(COL_RECIPE_NAME, name);
        values.put(COL_INSTRUCTIONS, instructions);
        return db.insert(TABLE_RECIPES, null, values);
    }

    private void insertIngredient(SQLiteDatabase db, long recipeId, String name, double qty) {
        ContentValues values = new ContentValues();
        values.put(COL_ING_RECIPE_ID, recipeId);
        values.put(COL_ING_NAME, name);
        values.put(COL_ING_QTY, qty);
        db.insert(TABLE_RECIPE_INGREDIENTS, null, values);
    }
    // ---------- STRICT MATCHING ALGORITHM ----------

    public List<Recipe> getSuggestedRecipes() {
        List<Recipe> suggested = new ArrayList<>();

        // 1. Get the user's current pantry and put it in a Map for quick lookup
        // We combine quantities if the user added the same item twice
        Map<String, Double> pantryMap = new HashMap<>();
        for (PantryItem item : getAllPantryItems()) {
            String normName = normalizeName(item.getName());
            double currentQty = pantryMap.getOrDefault(normName, 0.0);
            pantryMap.put(normName, currentQty + item.getQuantity());
        }

        // 2. Get all recipes from the database
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPES, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_RECIPE_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_RECIPE_NAME));
                String instructions = cursor.getString(cursor.getColumnIndexOrThrow(COL_INSTRUCTIONS));

                // 3. Get the ingredients required for this recipe
                List<RecipeIngredient> requiredIngredients = getIngredientsForRecipe(id, db);

                // 4. Apply the strict-matching rule
                if (canMakeRecipe(requiredIngredients, pantryMap)) {
                    Recipe recipe = new Recipe(id, name, instructions);
                    recipe.setIngredients(requiredIngredients);
                    suggested.add(recipe);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return suggested;
    }

    // Checks if the pantry has EVERY ingredient in AT LEAST the required quantity
    private boolean canMakeRecipe(List<RecipeIngredient> ingredients, Map<String, Double> pantryMap) {
        for (RecipeIngredient req : ingredients) {
            String normName = normalizeName(req.getName());

            // If the ingredient is missing entirely -> FAIL
            if (!pantryMap.containsKey(normName)) {
                return false;
            }

            // If the user has some, but not enough -> FAIL
            if (pantryMap.get(normName) < req.getQuantity()) {
                return false;
            }
        }
        // If the loop finishes, they have everything!
        return true;
    }

    private List<RecipeIngredient> getIngredientsForRecipe(long recipeId, SQLiteDatabase db) {
        List<RecipeIngredient> list = new ArrayList<>();
        Cursor cursor = db.query(TABLE_RECIPE_INGREDIENTS, null,
                COL_ING_RECIPE_ID + " = ?", new String[]{String.valueOf(recipeId)},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_ING_NAME));
                double qty = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_ING_QTY));
                list.add(new RecipeIngredient(name, qty));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Helper to make matching robust (handles "Tomato" vs "tomatoes")
    private String normalizeName(String name) {
        String n = name.toLowerCase().trim();
        if (n.endsWith("es")) {
            n = n.substring(0, n.length() - 2);
        } else if (n.endsWith("s")) {
            n = n.substring(0, n.length() - 1);
        }
        return n;
    }
}