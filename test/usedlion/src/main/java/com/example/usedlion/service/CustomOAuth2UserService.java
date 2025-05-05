package com.example.usedlion.service;

import com.example.usedlion.dto.Member;
import com.example.usedlion.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User user = super.loadUser(request);
        Map<String, Object> attributes = user.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String provider = request.getClientRegistration().getRegistrationId();
        String providerId = (String) attributes.get("sub");

        Member existing = memberRepository.findByEmail(email);
        if (existing == null) {
            Member member = new Member();
            member.setEmail(email);
            member.setName(name);
            member.setProvider(provider);
            member.setProviderId(providerId);
            member.setRole("USER");
            memberRepository.save(member);
        }

        return user; // You may want to wrap it in a custom class if you plan to use roles
    }
}
