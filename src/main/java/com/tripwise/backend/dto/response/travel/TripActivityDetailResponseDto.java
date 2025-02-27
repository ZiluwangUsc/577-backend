package com.tripwise.backend.dto.response.trip;

public class TripActivityDetailResponseDto {
    private int code;
    private TripActivityDto activity;

    public TripActivityDetailResponseDto() {
    }

    public TripActivityDetailResponseDto(int code, TripActivityDto activity) {
        this.code = code;
        this.activity = activity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public TripActivityDto getActivity() {
        return activity;
    }

    public void setActivity(TripActivityDto activity) {
        this.activity = activity;
    }
}
