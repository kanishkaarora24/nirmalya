//
//  LoginViewController.swift
//  Nirmalya_public
//
//  Created by Ayush Verma on 25/06/18.
//  Copyright © 2018 Ayush Verma. All rights reserved.
//

import UIKit
import Firebase

class LoginViewController: UIViewController,UITextFieldDelegate {

    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
            allUITweaks()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if textField == emailTextField{
            passwordTextField.becomeFirstResponder()
        }
        else if textField == passwordTextField{
            textField.resignFirstResponder()
        }
        return true
    }
    
    func allUITweaks(){
        
        emailTextField.delegate = self
        passwordTextField.delegate = self
        
        
        let paddingView = UIImageView(frame: CGRect(x: 0.0, y: 0.0, width: 45.0, height: (self.emailTextField.frame.height - 10.0)))
        paddingView.image = UIImage(named: "man")
        paddingView.clipsToBounds = true
        paddingView.contentMode = .scaleAspectFit
        paddingView.tintColor = UIColor.black
        emailTextField.leftView = paddingView
        emailTextField.leftViewMode = UITextFieldViewMode.always
        
        let passwordpadding = UIImageView(frame: CGRect(x: 0.0, y: 0.0, width: 50.0, height: (self.passwordTextField.frame.height - 11)))
        passwordpadding.image = UIImage(named: "password")
        passwordpadding.clipsToBounds = true
        passwordpadding.contentMode = .scaleAspectFit
        passwordpadding.tintColor = UIColor.black
        passwordTextField.leftView = passwordpadding
        passwordTextField.leftViewMode = UITextFieldViewMode.always
        
        emailTextField.layer.cornerRadius = emailTextField.frame.size.height/2
        emailTextField.clipsToBounds = true
        passwordTextField.layer.cornerRadius = passwordTextField.frame.size.height/2
        passwordTextField.clipsToBounds = true
        
        
    }
    
    
    @IBAction func loginButtonPressed(_ sender: Any) {
        
        guard let email = emailTextField.text else { return }
        guard let password = passwordTextField.text else { return }
        
        Auth.auth().signIn(withEmail: email, password: password)
        
    }
    

}
