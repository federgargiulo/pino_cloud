import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';
import { ConfirmDialogComponent, ConfirmDialogData } from './confirm-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class ConfirmDialogService {

  constructor(
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) { }

  showConfirm(title: string, message: string, confirmText?: string, cancelText?: string): Observable<boolean> {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '450px',
      data: {
        title,
        message,
        type: 'confirm',
        confirmText: confirmText || 'Confirm',
        cancelText: cancelText || 'Cancel'
      } as ConfirmDialogData
    });

    return dialogRef.afterClosed();
  }

  showError(title: string, message: string): Observable<boolean> {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '450px',
      data: {
        title,
        message,
        type: 'error'
      } as ConfirmDialogData
    });

    return dialogRef.afterClosed();
  }

  showSuccess(message: string, action: string = 'OK'): void {
    this.snackBar.open(message, action, {
      duration: 3000,
      horizontalPosition: 'end',
      verticalPosition: 'top',
      panelClass: ['success-snackbar']
    });
  }

  showInfo(title: string, message: string): Observable<boolean> {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '450px',
      data: {
        title,
        message,
        type: 'info'
      } as ConfirmDialogData
    });

    return dialogRef.afterClosed();
  }
}
