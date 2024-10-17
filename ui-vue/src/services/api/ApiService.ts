import { AxiosInstance, AxiosResponse } from 'axios'
import { AxiosService } from '../AxiosService.ts'
import { JsonResponse, Page, Pageable } from './types.ts'
import { authService, AuthService } from '../auth/AuthService.ts'
import { AppRoutePaths } from '../../AppRoutes.ts'

type Response<Type> = Page<Type> | JsonResponse<Type>

export class ApiService {
  constructor(
    private axiosService: AxiosService,
    private authService: AuthService
  ) {}

  findPage<T = any, P = Response<T>>(uri: string, params?: any, pageable?: Pageable, cancelToken?): Promise<P> {
    const parameters = {
      ...params,
      _size: pageable?.pageSize,
      _page: pageable?.pageNumber,
      cancelToken,
    }
    return this.get(uri, parameters).then((response) => {
      return response.data
    })
  }

  get(uri: string, params?: any): Promise<AxiosResponse> {
    return this.getAxioInstance().get(uri, {
      params: params,
      signal: this.axiosService.newAbortSignal(5000),
    })
  }

  post(uri: string, data: any): Promise<AxiosResponse> {
    return this.getAxioInstance().post(uri, data)
  }

  private getAxioInstance(): AxiosInstance {
    const axiosInstance = this.axiosService.getAxiosInstance()
    if (this.authService.isAuthenticated()) {
      axiosInstance.defaults.headers.common['Authorization'] = `Bearer ${this.authService.getAuthContext().value?.jwtToken}`
    }

    axiosInstance.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error.response.status === 401) {
          this.authService.resetAuthContext()
          window.location.href = AppRoutePaths.AUTH_LOGIN
        } else {
          return error
        }
      }
    )

    return axiosInstance
  }
}

export const apiService = new ApiService(new AxiosService(), authService)
