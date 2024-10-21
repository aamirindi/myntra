import { Component, OnInit } from '@angular/core';
import { AngularFireAuth } from '@angular/fire/compat/auth';
import { Router } from '@angular/router';
import firebase from 'firebase/compat/app';
import 'firebase/compat/auth';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.css'],
})
export class AuthComponent implements OnInit {
  phoneNumber: string = '';
  verificationCode: string = '';
  verificationId: string | null = null;
  showVerificationInput: boolean = false;
  signupError: string | null = null;

  private recaptchaVerifier!: firebase.auth.RecaptchaVerifier;

  constructor(private afAuth: AngularFireAuth, private router: Router) {}

  ngOnInit() {
    setTimeout(() => {
      // Initialize reCAPTCHA verifier
      this.recaptchaVerifier = new firebase.auth.RecaptchaVerifier('recaptcha-container', {
        size: 'normal', // Change to 'normal' for testing
        callback: (response: any) => {
          // reCAPTCHA solved - will automatically trigger verification
        },
        'expired-callback': () => {
          // Handle reCAPTCHA expiration
          this.signupError = 'reCAPTCHA expired. Please try again.';
        },
      });
      
    }, 100); // Adjust the timeout value if necessary
  }

  async sendVerificationCode() {
    try {
      console.log('Initializing reCAPTCHA verifier...');
      if (!this.recaptchaVerifier) {
        throw new Error('reCAPTCHA verifier is not initialized.');
      }
  
      console.log('Sending verification code to', this.phoneNumber);
      const confirmationResult = await this.afAuth.signInWithPhoneNumber(
        this.phoneNumber,
        this.recaptchaVerifier
      );
  
      console.log('Verification code sent');
      this.verificationId = confirmationResult.verificationId;
      this.showVerificationInput = true;
    } catch (error) {
      console.error('Error sending verification code', error);
      this.signupError = 'Failed to send verification code. Please try again later.';
    }
  }
  

  async verifyCode() {
    if (!this.verificationId) {
      this.signupError = 'Verification ID is missing.';
      return;
    }

    try {
      // Create a credential with the verification code
      const credential = firebase.auth.PhoneAuthProvider.credential(
        this.verificationId,
        this.verificationCode
      );

      // Sign in with the credential
      await this.afAuth.signInWithCredential(credential);
    } catch (error) {
      console.error('Error verifying code', error);
      this.signupError = 'Verification failed. Please try again later.';
    }
  }
}


