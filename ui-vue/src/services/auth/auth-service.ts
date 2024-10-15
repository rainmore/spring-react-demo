import { AxiosInstance } from 'axios'
import { AxiosService } from '../axios-service.ts'
import { AuthContext, CurrentUser } from './types'
import { jwtDecode, JwtHeader } from 'jwt-decode'
import { dateTimeService } from '../date-time-service.ts'
import { ref, Ref } from 'vue'

interface Login {
  username: string
  password: string
}

const authContext: Ref<AuthContext | null> = ref(null)

class AuthService {
  private axiosInstance: AxiosInstance

  constructor(private axiosService: AxiosService) {
    this.axiosInstance = this.axiosService.getAxiosInstance()
  }

  login(LoginDto: Login): Promise<AuthContext> {
    return this.axiosInstance.post('/auth/login', LoginDto).then((response) => {
      const jwtToken = response.headers['jwt-token']
      const currentUser: CurrentUser = response.data.data as CurrentUser

      authContext.value = {
        currentUser,
        jwtToken,
      }

      this.setStoreAuthContext(authContext.value)
      return authContext.value
    })
  }

  isAuthenticated(): boolean {
    if (!this.hasJwtToken()) {
      return false
    }

    const token:JwtHeader = jwtDecode(this.getAuthContext().value?.jwtToken)
    const expireDate = dateTimeService.timestampToDate(token?.exp)

    return expireDate > new Date()
  }

  hasJwtToken(): boolean {
    return this.getAuthContext().value !== null
  }

  getStoredAuthContext(): AuthContext | null {
    const data = localStorage.getItem('AuthContext')
    return data ? JSON.parse(data) : null
  }

  getAuthContext(): Ref<AuthContext | null> {
    if (authContext.value === null) {
      authContext.value = this.getStoredAuthContext()
    }
    return authContext
  }

  setStoreAuthContext(authContext: AuthContext): void {
    return localStorage.setItem('AuthContext', JSON.stringify(authContext))
  }

  resetAuthContext(): void {
    authContext.value = null
    localStorage.removeItem('AuthContext')
  }

}

export type { AuthService }
export const authService = new AuthService(new AxiosService())
