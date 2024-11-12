package com.solt.popcornatic.movies.data.repositoryimport android.text.TextUtilsimport android.util.Logimport androidx.core.text.TextUtilsCompatimport com.solt.popcornatic.ApiResultimport com.solt.popcornatic.AppendToResponseOptionsimport com.solt.popcornatic.US_ENGLISHimport com.solt.popcornatic.movies.data.api.MovieApiImplimport com.solt.popcornatic.movies.data.model.TrendingApiResultimport kotlinx.coroutines.Dispatchersimport kotlinx.coroutines.withContextimport retrofit2.HttpExceptionimport java.io.IOExceptionimport javax.inject.Injectclass MovieRepositoryImpl @Inject constructor(val movieApi:MovieApiImpl):MovieRepository {    override suspend fun getTrendingMovies(page: Int, language: String?): ApiResult {        return try {                val result = movieApi.getTrendingMovies(page, language?: US_ENGLISH)                 ApiResult.Success(result)            } catch (e: IOException) {                 ApiResult.Failure.ApiFailure(e)            } catch (e: HttpException) {                ApiResult.Failure.NetworkFailure(e)            }    }    override suspend fun getPopularMovies(page: Int, language: String?): ApiResult {        return try {            val result = movieApi.getPopularMovies(page, language?: US_ENGLISH)            ApiResult.Success(result)        } catch (e: IOException) {            ApiResult.Failure.ApiFailure(e)        } catch (e: HttpException) {            ApiResult.Failure.NetworkFailure(e)        }    }    override suspend fun getUpcomingMovies(page: Int, language: String?): ApiResult {        return try {            val result = movieApi.getUpcomingMovies(page, language?: US_ENGLISH)            ApiResult.Success(result)        } catch (e: IOException) {            ApiResult.Failure.ApiFailure(e)        } catch (e: HttpException) {            ApiResult.Failure.NetworkFailure(e)        }    }    override suspend fun getTopRatedMovies(page: Int, language: String?): ApiResult {        return try {            val result = movieApi.getTopRatedMovies(page, language?: US_ENGLISH)            ApiResult.Success(result)        } catch (e: IOException) {            ApiResult.Failure.ApiFailure(e)        } catch (e: HttpException) {            ApiResult.Failure.NetworkFailure(e)        }    }    override suspend fun getMovieDetailsById(        movieId: Int,        appendToResponseOptions: List<AppendToResponseOptions>    ): ApiResult {        return try {            val listOfAppendToResponseOptions = appendToResponseOptions.ifEmpty { listOf(AppendToResponseOptions.VIDEOS,AppendToResponseOptions.IMAGES,AppendToResponseOptions.CREDITS) }            //Convert the list into a string with each separating a comma             val queryString = listOfAppendToResponseOptions.joinToString (separator = ","){ it.title }            Log.i("Fad",queryString)            val movieDetailsResult = movieApi.getMovieDetailOfMovieById(movieId,queryString)            ApiResult.Success(movieDetailsResult)        }catch (e:IOException){            ApiResult.Failure.NetworkFailure(e)        }catch (e:HttpException){            ApiResult.Failure.ApiFailure(e)        }    }    override suspend fun getMovieRecommendations(movieId: Int,page: Int): ApiResult {        return  try {         val movieRecommendations = movieApi.getMovieRecommendations(movieId,page)            ApiResult.Success(movieRecommendations)        }catch (e:IOException){            ApiResult.Failure.NetworkFailure(e)        }catch (e:HttpException){            ApiResult.Failure.ApiFailure(e)        }    }    override suspend fun getSimilarMovies(movieId: Int,page: Int): ApiResult {        return  try {            val similarMovies = movieApi.getSimilarMovies(movieId,page)            ApiResult.Success(similarMovies)        }catch (e:IOException){            ApiResult.Failure.NetworkFailure(e)        }catch (e:HttpException){            ApiResult.Failure.ApiFailure(e)        }    }    override suspend fun getProductionCompanyDetailsById(companyId: Int): ApiResult {        return  try {           val companyDetails = movieApi.getProductionCompanyDetailsById(companyId)            ApiResult.Success(companyDetails)        }catch (e:IOException){            ApiResult.Failure.NetworkFailure(e)        }catch (e:HttpException){            ApiResult.Failure.ApiFailure(e)        }    }}