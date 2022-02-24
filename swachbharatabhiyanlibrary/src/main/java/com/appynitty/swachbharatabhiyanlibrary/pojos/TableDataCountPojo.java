package com.appynitty.swachbharatabhiyanlibrary.pojos;

public class TableDataCountPojo {

    public class WorkHistory {

        private String houseCollection;
        private String LiquidCollection;
        private String StreetCollection;

        private String date;

        private String pointCollection;

        private String DumpYardCollection;
        //added by Rahul Rokade
        private String ResidentialCollection;
        private String ResidentialBCollection;
        private String ResidentialSCollection;
        private String CommertialCollection;
        private String CADCollection;
        private String HorticultureCollection;
        private String CTPTCollection;
        private String SLWMCollection;
        private String HouseCType;

        public String getCtptCollection() {
            return CTPTCollection;
        }

        public void setCtptCollection(String ctptCollection) {
            CTPTCollection = ctptCollection;
        }

        public String getSwmCollection() {
            return SLWMCollection;
        }

        public void setSwmCollection(String swmCollection) {
            SLWMCollection = swmCollection;
        }

        public String getResidentialCollection() {
            return ResidentialCollection;
        }

        public void setResidentialCollection(String residentialCollection) {
            ResidentialCollection = residentialCollection;
        }

        public String getResidentialBCollection() {
            return ResidentialBCollection;
        }

        public void setResidentialBCollection(String residentialBCollection) {
            ResidentialBCollection = residentialBCollection;
        }

        public String getResidentialSCollection() {
            return ResidentialSCollection;
        }

        public void setResidentialSCollection(String residentialSCollection) {
            ResidentialSCollection = residentialSCollection;
        }

        public String getCommertialCollection() {
            return CommertialCollection;
        }

        public void setCommertialCollection(String commertialCollection) {
            CommertialCollection = commertialCollection;
        }


        public String getCADCollection() {
            return CADCollection;
        }

        public void setCADCollection(String cadCollection) {
            this.CADCollection = cadCollection;
        }

        public String getHorticultureCollection() {
            return HorticultureCollection;
        }

        public void setHorticultureCollection(String horticultureCollection) {
            HorticultureCollection = horticultureCollection;
        }

        public String getHouseCollection() {
            return houseCollection;
        }

        public void setHouseCollection(String houseCollection) {
            this.houseCollection = houseCollection;
        }

        public String getLiquidCollection() {
            return LiquidCollection;
        }

        public void setLiquidCollection(String liquidCollection) {
            this.LiquidCollection = liquidCollection;
        }

        public String getStreetCollection() {
            return StreetCollection;
        }

        public void setStreetCollection(String StreetCollection) {
            this.StreetCollection = StreetCollection;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPointCollection() {
            return pointCollection;
        }

        public void setPointCollection(String pointCollection) {
            this.pointCollection = pointCollection;
        }

        public String getDumpYardCollection() {
            return DumpYardCollection;
        }

        public void setDumpYardCollection(String dumpYardCollection) {
            DumpYardCollection = dumpYardCollection;
        }

        @Override
        public String toString() {
            return "TableDataCountPojo{"
                    + "houseCollection='" + houseCollection + '\''
                    + ", date='" + date + '\''
                    + ", pointCollection='" + pointCollection + '\''
                    + ", DumpYardCollection='" + DumpYardCollection + '\''
                    + ", LiquidCollection='" + LiquidCollection + '\''
                    + ", StreetCollection='" + StreetCollection + '\''
                    + ", CADCollection='" + CADCollection + '\''
                    + ", HorticultureCollection='" + HorticultureCollection + '\''
                    + ", ResidentialCollection='" + ResidentialCollection + '\''
                    + ", ResidentialBCollection='" + ResidentialBCollection + '\''
                    + ", ResidentialSCollection='" + ResidentialSCollection + '\''
                    + ", CommertialCollection='" + CommertialCollection + '\''
                    + ", CTPTCollection='" + CTPTCollection + '\''
                    + ", SLWMCollection='" + SLWMCollection + '\''
                    + '}';
        }
    }

    public class LocationCollectionCount {
        int CollectionCount;
        int LocationCount;

        public int getCollectionCount() {
            return CollectionCount;
        }

        public void setCollectionCount(int collectionCount) {
            CollectionCount = collectionCount;
        }

        @Override
        public String toString() {
            return "LocationCollectionCount{" +
                    "CollectionCount=" + CollectionCount +
                    ", LocationCount=" + LocationCount +
                    '}';
        }

        public int getLocationCount() {
            return LocationCount;
        }

        public void setLocationCount(int locationCount) {
            LocationCount = locationCount;
        }
    }

    public class WasteAddHistory {

    }
}
