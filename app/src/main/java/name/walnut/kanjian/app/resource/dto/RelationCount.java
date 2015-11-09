package name.walnut.kanjian.app.resource.dto;

import name.walnut.kanjian.app.resource.support.ResourceResult;

public class RelationCount extends ResourceResult {

    /** 未读消息数*/
    private int unreadCount;
    /** 好友数*/
	private int friendsCount;

	public RelationCount(int unreadCount, int friendsCount) {
		this.unreadCount = unreadCount;
		this.friendsCount = friendsCount;
	}
	public int getUnreadCount() {
		return unreadCount;
	}
	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}
	public int getFriendsCount() {
		return friendsCount;
	}
	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}
	
	
}
