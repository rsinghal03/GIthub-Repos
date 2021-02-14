package com.example.githubrepos.model.remote.issuesresponse

class IssuesResponse : ArrayList<IssuesResponseItem>()

data class IssuesResponseItem(
    val active_lock_reason: Any,
    val assignee: Any,
    val assignees: List<Any>,
    val author_association: String,
    val body: String,
    val closed_at: Any,
    val comments: Int,
    val comments_url: String,
    val created_at: String,
    val events_url: String,
    val html_url: String,
    val id: Int,
    val labels: List<Any>,
    val labels_url: String,
    val locked: Boolean,
    val milestone: Any,
    val node_id: String,
    val number: Int,
    val performed_via_github_app: Any,
    val pull_request: PullRequest,
    val repository_url: String,
    val state: String,
    val title: String,
    val updated_at: String,
    val url: String,
    val user: User
)

data class PullRequest(
    val diff_url: String,
    val html_url: String,
    val patch_url: String,
    val url: String
)

data class User(
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    val id: Int,
    val login: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
)