package com.tripwise.backend.dto.response.trip;

public class TripDeleteResponseDto {
    private int code;

    public TripDeleteResponseDto() {
    }

    public TripDeleteResponseDto(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
