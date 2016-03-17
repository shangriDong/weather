package com.example.administrator.myapplication;

import java.util.List;

/**
 * Created by Shangri on 2016/3/16.
 */
public class MyLocation {
    private Location mLocation;
    private AddressComponent mAddressComponent;
    private Point mPoint;
    private Pois mPois;
    private Result mResult;
    private Root mRoot;

    public MyLocation () {
        mLocation = new Location();
        mAddressComponent = new AddressComponent();
        mPoint = new Point();
        mPois = new Pois();
        mResult = new Result();
        mRoot = new Root();
    }

    public Location getLocation() {
         return mLocation;
    }

    public AddressComponent getAddressComponent() {
        return mAddressComponent;
    }

    public Point getPoint() {
        return mPoint;
    }
    public Result getResult() {
        return mResult;
    }

    public Root getRoot() {
        return mRoot;
    }

    final  public class Location {
        private double lng;

        private double lat;

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLng() {
            return this.lng;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLat() {
            return this.lat;
        }

    }

    final public class AddressComponent {
        private String adcode;

        private String city;

        private String country;

        private String direction;

        private String distance;

        private String district;

        private String province;

        private String street;

        private String street_number;

        private int country_code;

        public void setAdcode(String adcode) {
            this.adcode = adcode;
        }

        public String getAdcode() {
            return this.adcode;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity() {
            return this.city;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountry() {
            return this.country;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getDirection() {
            return this.direction;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDistance() {
            return this.distance;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getDistrict() {
            return this.district;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getProvince() {
            return this.province;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getStreet() {
            return this.street;
        }

        public void setStreet_number(String street_number) {
            this.street_number = street_number;
        }

        public String getStreet_number() {
            return this.street_number;
        }

        public void setCountry_code(int country_code) {
            this.country_code = country_code;
        }

        public int getCountry_code() {
            return this.country_code;
        }

    }

    final public class Point {
        private double x;

        private double y;

        public void setX(double x) {
            this.x = x;
        }

        public double getX() {
            return this.x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getY() {
            return this.y;
        }

    }

    final public class Pois {
        private String addr;

        private String cp;

        private String direction;

        private String distance;

        private String name;

        private String poiType;

        private Point point;

        private String tag;

        private String tel;

        private String uid;

        private String zip;

        public void setAddr(String addr) {
            this.addr = addr;
        }

        public String getAddr() {
            return this.addr;
        }

        public void setCp(String cp) {
            this.cp = cp;
        }

        public String getCp() {
            return this.cp;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public String getDirection() {
            return this.direction;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDistance() {
            return this.distance;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setPoiType(String poiType) {
            this.poiType = poiType;
        }

        public String getPoiType() {
            return this.poiType;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public Point getPoint() {
            return this.point;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getTag() {
            return this.tag;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getTel() {
            return this.tel;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUid() {
            return this.uid;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getZip() {
            return this.zip;
        }

    }

    public class Result {
        private Location location;

        private String formatted_address;

        private String business;

        private AddressComponent addressComponent;

        private List<Pois> pois;

        private List<Pois> poiRegions;

        private String sematic_description;

        private int cityCode;

        public void setLocation(Location location) {
            this.location = location;
        }

        public Location getLocation() {
            return this.location;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public String getFormatted_address() {
            return this.formatted_address;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public String getBusiness() {
            return this.business;
        }

        public void setAddressComponent(AddressComponent addressComponent) {
            this.addressComponent = addressComponent;
        }

        public AddressComponent getAddressComponent() {
            return this.addressComponent;
        }

        public void setPois(List<Pois> pois) {
            this.pois = pois;
        }

        public List<Pois> getPois() {
            return this.pois;
        }

        public void setPoiRegions(List<Pois> poiRegions) {
            this.poiRegions = poiRegions;
        }

        public List<Pois> getPoiRegions() {
            return this.poiRegions;
        }

        public void setSematic_description(String sematic_description) {
            this.sematic_description = sematic_description;
        }

        public String getSematic_description() {
            return this.sematic_description;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }

        public int getCityCode() {
            return this.cityCode;
        }

    }

    public class Root {
        private int status;

        private Result result;

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return this.status;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public Result getResult() {
            return this.result;
        }

    }
}