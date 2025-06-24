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
  tenantId: string;
  token: string;
}

export interface OnboardVerifyRequest {
  tenantId: string;
  token: string;
  code: string;
}

export interface OnboardVerifyResponse {
  verified: boolean;
}

export interface BroadcastMessage {
  id: string;
  text: string;
  scheduledAt: string;
  status: string;
}

export interface BroadcastCreateRequest {
  text: string;
  scheduledAt: string;
}
