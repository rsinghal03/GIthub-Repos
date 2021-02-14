package com.example.githubrepos.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubrepos.model.remote.repositoryresponse.Item
import com.example.githubrepos.networking.GithubApiService
import retrofit2.HttpException
import java.io.IOException

class PageKeyedGithubRepoPagingSource(
    private val githubApiService: GithubApiService,
    private val query: String,
    private val perPage: Int
) : PagingSource<Int, Item>() {

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        return try {
            val query = "$query language:$query"
            val nextPage = params.key ?: 1
            val items = githubApiService.getRepos(
                query = query,
                page = nextPage,
                perPage = perPage
            )

            LoadResult.Page(
                data = items.items,
                prevKey = null, // only paging forward
                nextKey = if (nextPage < items.total_count) nextPage.plus(1) else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}