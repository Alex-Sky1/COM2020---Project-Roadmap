package com.waste_manager.team_roadmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<Customer> cList = customerRepository.findByDName(username);
        if(!cList.isEmpty()){
            Customer c = cList.getFirst();
            return User.withUsername(c.getdName()).password(c.getPassword()).authorities("ROLE_CUSTOMER").build();
        }
        List<Seller> sList = sellerRepository.findByDName(username);
        if(!sList.isEmpty()){
            Seller s = sList.getFirst();
            return User.withUsername(s.getName()).password(s.getPassword()).authorities("ROLE_SELLER").build();
        }


        throw new UsernameNotFoundException("not found");
    }

}
