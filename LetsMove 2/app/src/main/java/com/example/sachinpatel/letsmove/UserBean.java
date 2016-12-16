package com.example.sachinpatel.letsmove;


public class UserBean {
     String post_id;
    String user_id;
    String name;
    String type;
    String weight;
    String from_address;
    String to_address;
    String pic_name;
    String max_amount;
    String pickup_date;
    String status;



    public String getPost_id() { return post_id; }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public void setPost_id(String post_id) {this.post_id = post_id; }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public void setMax_amount(String max_amount) {
        this.max_amount = max_amount;
    }

    public void setStatus(String status) { this.status = status; }

    public String getType() {
        return type;
    }

    public String getWeight() {
        return weight;
    }

    public String getFrom_address() {
        return from_address;
    }

    public String getTo_address() {
        return to_address;
    }

    public String getPic_name() {
        return pic_name;
    }

    public String getMax_amount() {
        return max_amount;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public String getStatus() { return status; }
}
