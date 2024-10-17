<script setup lang="ts">
import { authService } from '../../services/auth/AuthService';
import { UrlService } from '../../services/UrlService';
import { AppRoutePaths } from '../../AppRoutes';
import { toastService } from '../../services/ToastService';
import { ref } from 'vue';


const urlService = new UrlService()
const loading = ref(false)

const onSubmit = (event: any) => {
  console.log(event.target.value)
  loading.value = true
  authService
    .login({
      username: event.target.email.value,
      password: event.target.password.value,
    })
    .then(() => {
      const currentUser = authService.getAuthContext().value?.currentUser

      // setCurrentUser(currentUser)
      toastService.success(`Welcome back, ${currentUser?.account.firstname}!`)
      urlService.redirect(AppRoutePaths.DASHBOARD)
    })
    .catch((error) => {

      toastService.error(error.message)
    })


  event.preventDefault()
}

const onReset = (event: any) => {
  loading.value = false
  event.preventDefault()
}

if (authService.isAuthenticated()) {
  toastService.info(`You've logged in!`)
  urlService.redirect(AppRoutePaths.DASHBOARD)
}

</script>

<template>
  <form @submit="onSubmit">
    <section class="hero box">
      <div class="hero-body">
        <h1 class="title">Login</h1>
        <div class="hero-body container">
          <div class="field">
            <label class="label">Email</label>
            <div class="control has-icons-left has-icons-right">
              <input type="email" name="email" maxLength="250" placeholder="Please enter email" class="input"
                required="true" />
              <span class="icon is-small is-left">
                <FontAwesomeIcon icon="envelope" />
              </span>
              <span class="icon is-small is-right">
                <FontAwesomeIcon icon="check" />
              </span>
            </div>
          </div>
          <div class="field">
            <label class="label">Password</label>
            <div class="control has-icons-left">
              <input type="password" name="password" maxLength="50" placeholder="Please enter password" class="input"
                required="true" />
              <span class="icon is-small is-left">
                <FontAwesomeIcon icon="lock" />
              </span>
            </div>
          </div>
          <div class="field is-grouped mt-4">
            <div class="control">
              <button class="button is-link is-light" type="reset" @click="onReset">
                Cancel
              </button>
            </div>

            <div class="control">
              <button class="button is-link" :class="{ 'is-loading': loading }">
                Login
              </button>
            </div>
          </div>

          <div class="field is-grouped mt-4">
            <div class="control">
              <a href="#">Forget password?</a>
            </div>
          </div>
        </div>
      </div>
    </section>
  </form>
</template>