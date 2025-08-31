import { AuthInterceptor } from './auth.interceptor';
import { AuthService } from './auth.service';
import { HttpRequest, HttpHandler } from '@angular/common/http';

describe('AuthInterceptor', () => {
  let interceptor: AuthInterceptor;
  let authServiceSpy: jasmine.SpyObj<AuthService>;

  beforeEach(() => {
    authServiceSpy = jasmine.createSpyObj('AuthService', ['getToken', 'logout']);
    interceptor = new AuthInterceptor(authServiceSpy);
  });

  it('should be created', () => {
    expect(interceptor).toBeTruthy();
  });

  // puoi aggiungere altri test qui
});
