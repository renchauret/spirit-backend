package com.chauret.api

import com.chauret.api.request.IngredientRequest
import com.chauret.api.request.runWithBodyAndResponse
import com.chauret.api.request.runWithBodyAndUsernameAndResponse
import com.chauret.api.request.runWithUsernameAndResponse
import com.chauret.api.response.SuccessfulResponseType
import com.chauret.api.response.runWithResponse
import com.chauret.model.Permissions
import com.chauret.service.IngredientService
import io.kotless.dsl.lang.http.Get
import io.kotless.dsl.lang.http.Post
import io.kotless.dsl.lang.http.Put
import java.util.UUID

private const val ROUTE_PREFIX = "/ingredient"
private const val ADMIN_ROUTE_PREFIX = "/admin$ROUTE_PREFIX"

@Post(ADMIN_ROUTE_PREFIX)
fun createAdminIngredient() = runWithBodyAndResponse<IngredientRequest> (SuccessfulResponseType.CREATED) { body ->
    IngredientService.createIngredient(body, Permissions.ADMIN.name)
}

@Post(ROUTE_PREFIX)
fun createIngredient() = runWithBodyAndUsernameAndResponse<IngredientRequest>(SuccessfulResponseType.CREATED) { body, username ->
    IngredientService.createIngredient(body, username)
}

@Put(ADMIN_ROUTE_PREFIX)
fun editAdminIngredient(guid: String) = runWithBodyAndResponse<IngredientRequest>(SuccessfulResponseType.OK) { body ->
    IngredientService.editIngredient(body, Permissions.ADMIN.name, UUID.fromString(guid))
}

@Put(ROUTE_PREFIX)
fun editIngredient(guid: String) = runWithBodyAndUsernameAndResponse<IngredientRequest>(SuccessfulResponseType.OK) { body, username ->
    IngredientService.editIngredient(body, username, UUID.fromString(guid))
}

@Get(ADMIN_ROUTE_PREFIX)
fun getAdminIngredient(guid: String) = runWithResponse(SuccessfulResponseType.OK) {
    IngredientService.getIngredient(UUID.fromString(guid), Permissions.ADMIN.name)
}

@Get(ROUTE_PREFIX)
fun getUserIngredient(guid: String) = runWithUsernameAndResponse(SuccessfulResponseType.OK) { username ->
    IngredientService.getIngredient(UUID.fromString(guid), username)
}

@Get("$ADMIN_ROUTE_PREFIX/all")
fun getAdminIngredients() = runWithResponse(SuccessfulResponseType.OK) {
    IngredientService.getIngredientsForUser(Permissions.ADMIN.name)
}

@Get("$ROUTE_PREFIX/all")
fun getUserIngredients() = runWithUsernameAndResponse(SuccessfulResponseType.OK) { username ->
    IngredientService.getIngredientsForUser(username)
}