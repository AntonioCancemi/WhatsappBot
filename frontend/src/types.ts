export interface User {
  id: string;
  phone: string;
  name?: string;
  lastMessage?: string;
}

export interface Message {
  id: string;
  userId: string;
  text: string;
  direction: 'IN' | 'OUT';
  timestamp: string;
}

export interface Template {
  name: string;
}

export interface OnboardStartRequest {
  businessName: string;
  phoneNumber: string;
}

export interface OnboardStartResponse {
  tenantNameId: string;
  token: string;
}

export interface OnboardVerifyRequest {
  tenantNameId: string;
  token: string;
  code: string;
}

export interface OnboardVerifyResponse {
  verified: boolean;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

export interface RegisterRequest {
  email: string;
  fullName: string;
  password: string;
  tenantName: string;
}

export interface Role {
  id: string;
  name: string;
}

export interface ServiceItem {
  id: string;
  name: string;
  durationMinutes: number;
  bufferMinutes: number;
  maxPerSlot: number;
}

export interface Slot {
  time: string;
  remaining: number;
}

export interface Booking {
  id: string;
  userId: string;
  serviceId: string;
  date: string;
  time: string;
  status: string;
}
