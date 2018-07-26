//
//  GuestViewController.swift
//  Nirmalya_public
//
//  Created by Ayush Verma on 26/06/18.
//  Copyright © 2018 Ayush Verma. All rights reserved.
//

import UIKit
import Firebase

class GuestViewController: UIViewController,UIPickerViewDelegate,UIPickerViewDataSource,UITextFieldDelegate {
    
    @IBOutlet weak var nameTextField: UITextField!
    @IBOutlet weak var addressTextField: UITextField!
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var accountTypeButton: UIButton!
    @IBOutlet weak var registerButton: customButton!
    @IBOutlet weak var pickerView: UIPickerView!
    @IBOutlet weak var phoneNumberTextField: UITextField!
    
    var accountTypes = ["Customer","Trainer","Manufacturer"]
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        UISettings()
        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    func UISettings(){
        
        pickerView.delegate = self
        pickerView.dataSource = self
        nameTextField.delegate = self
        emailTextField.delegate = self
        addressTextField.delegate = self
        passwordTextField.delegate = self
        pickerView.isHidden = true
        
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        switch textField {
        case nameTextField:
            addressTextField.becomeFirstResponder()
        case addressTextField:
            phoneNumberTextField.becomeFirstResponder()
        case phoneNumberTextField:
            emailTextField.becomeFirstResponder()
        case emailTextField:
            passwordTextField.becomeFirstResponder()
        case passwordTextField:
                textField.resignFirstResponder()
        default:
            break
        }
        return true
    }

    func numberOfComponents(in pickerView: UIPickerView) -> Int {
        return 1
    }
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return accountTypes.count
    }
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return accountTypes[row]
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        accountTypeButton.setTitle(accountTypes[row], for: .normal)
        pickerView.isHidden = true
        registerButton.isHidden = false
    }
    
    @IBAction func accountTypeButtonPressed(_ sender: Any) {
        registerButton.isHidden = true
        pickerView.isHidden = false
    }

    @IBAction func registerButtonPressed(_ sender: Any) {
       
        createUser()
    }
    
    func createUser(){
        
        //create the user here.
        guard let Email = emailTextField.text else { return }
        guard let Password = passwordTextField.text else { return }
        
        
        if nameTextField.text != nil && phoneNumberTextField.text != nil && emailTextField.text != nil && passwordTextField.text != nil{
            
            Auth.auth().createUser(withEmail: Email, password: Password){user,error in
                let uid = Auth.auth().currentUser?.uid
                if error == nil && user != nil{
                    
                    print("User created with UID: \(String(describing: uid!))")
                    self.userDatabase()
                }
                else{
                    print("the error is",error!)
                    self.showAlert(title: "ERROR!", message: "Please check in all the fileds to continue.", buttonMessage: "Ok")
                }
            }
        }
    }
    
    func userDatabase(){
        
        let uid = Auth.auth().currentUser?.uid
        guard let email = emailTextField.text else { return }
        guard let phoneNumber = phoneNumberTextField.text else { return }
        guard let address = addressTextField.text else { return }
        guard let accountType = accountTypeButton.title(for: .normal) else { return }
        guard let name = nameTextField.text else { return }
        
        let databaseRef = Database.database().reference().child("registered_users/\(String(describing: uid!))")
            
            let dataObj = [
                
                "accountType" : accountType,
                "address": address,
                "email": email,
                "name": name,
                "phone": phoneNumber
                
                ] as [String : Any]
            
            databaseRef.setValue(dataObj)
        
    }
    
    func showAlert(title:String,message:String,buttonMessage:String){
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: buttonMessage, style: .cancel, handler: { (true) in
            //do whatever you want.
            self.nameTextField.becomeFirstResponder()
        }))
        present(alert, animated: true, completion: nil)
    }
    

}
