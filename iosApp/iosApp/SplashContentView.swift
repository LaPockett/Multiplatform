//
//  SplashContentView.swift
//  iosApp
//
//  Created by diandev on 26/1/26.
//

import SwiftUI

struct SplashContentView: View {

    @State private var isActive = false
    @State private var size = 0.8
    @State private var opacity = 0.5
    var body: some View {
        if isActive {
            ContentView()
        } else {
            VStack{
                VStack{
                    Image("LogoLaunch")
                        .resizable()
                        .scaledToFit()
                        .frame(width: 200, height: 200)
                        .font(.system(size: 80))
                        .foregroundColor(.white)
                }
                .scaleEffect(size)
                .opacity(opacity)
                .onAppear {
                    withAnimation(.easeIn(duration: 1.2)){
                        self.size = 0.9
                        self.opacity = 1.0
                    }
                }
            }
            .onAppear{
                DispatchQueue.main.asyncAfter(deadline: .now() + 2.0){
                    self.isActive = true
                }
            }
        }
    }
}
#Preview {
    SplashContentView()
}
