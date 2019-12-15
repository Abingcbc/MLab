package org.sse.communityservice.dto;

/**
 * @author HPY
 */
public class InputDTO {
    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    private String searchInput;

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    private boolean isNull;
}
