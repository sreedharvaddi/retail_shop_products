package com.retailshop.retailshopapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sreedhar on 1/14/18.
 */

public class RetailProduct implements Parcelable {

    String productId;
    String productName;
    String shortDescription;
    String longDescription;
    String price;
    String productImage;
    double reviewRating;
    int reviewCount;
    boolean inStock;

    protected RetailProduct(Parcel in) {
        productId = in.readString();
        productName = in.readString();
        shortDescription = in.readString();
        longDescription = in.readString();
        price = in.readString();
        productImage = in.readString();
        reviewRating = in.readDouble();
        reviewCount = in.readInt();
        inStock = in.readByte() != 0;
    }

    public static final Creator<RetailProduct> CREATOR = new Creator<RetailProduct>() {
        @Override
        public RetailProduct createFromParcel(Parcel in) {
            return new RetailProduct(in);
        }

        @Override
        public RetailProduct[] newArray(int size) {
            return new RetailProduct[size];
        }
    };

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public double getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(double reviewRating) {
        this.reviewRating = reviewRating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productId);
        parcel.writeString(productName);
        parcel.writeString(shortDescription);
        parcel.writeString(longDescription);
        parcel.writeString(price);
        parcel.writeString(productImage);
        parcel.writeDouble(reviewRating);
        parcel.writeInt(reviewCount);
        parcel.writeByte((byte) (inStock ? 1 : 0));
    }
}
