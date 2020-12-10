package edu.byu.cs.tweeter.client.model.service.response;

import java.util.List;
import java.util.Objects;

import edu.byu.cs.tweeter.client.model.domain.User;

/**
 * A paged response for a {@link edu.byu.cs.tweeter.client.model.service.request.FollowingRequest}.
 */
public class LiteFollowersResponse extends PagedResponse {

	private List<String> followers;

	/**
	 * Creates a response indicating that the corresponding request was unsuccessful. Sets the
	 * success and more pages indicators to false.
	 *
	 * @param message a message describing why the request was unsuccessful.
	 */
	public LiteFollowersResponse(String message) {
		super(false, message, false);
	}

	/**
	 * Creates a response indicating that the corresponding request was successful.
	 *
	 * @param followers the followers to be included in the result.
	 * @param hasMorePages an indicator of whether more data is available for the request.
	 */
	public LiteFollowersResponse(List<String> followers, boolean hasMorePages) {
		super(true, hasMorePages);
		this.followers = followers;
	}

	/**
	 * Returns the followers for the corresponding request.
	 *
	 * @return the followers.
	 */
	public List<String> getFollowers() {
		return followers;
	}

	@Override
	public boolean equals(Object param) {
		if (this == param) {
			return true;
		}

		if (param == null || getClass() != param.getClass()) {
			return false;
		}

		LiteFollowersResponse that = (LiteFollowersResponse) param;

		return (Objects.equals(followers, that.followers) &&
				Objects.equals(this.getMessage(), that.getMessage()) &&
				this.isSuccess() == that.isSuccess());
	}

	@Override
	public int hashCode() {
		return Objects.hash(followers);
	}
}