package com.example.demo.bean;

import java.util.List;

public class News {

    private String reason;

    private Result result;

    public String getReason() {
        return reason;
    }

    public Result getResult() {
        return result;
    }

    public static class Result{

        private String stat;

        private List<Data> data;

        public String getStat() {
            return stat;
        }

        public List<Data> getData() {
            return data;
        }
    }

    public static class Data{

        private String title;

        private String date;

        private String category;

        private String author_name;

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public String getCategory() {
            return category;
        }

        public String getAuthor_name() {
            return author_name;
        }
    }
}
