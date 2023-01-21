package com.coursework.socksapp.services.impl;

import com.coursework.socksapp.model.Color;
import com.coursework.socksapp.model.PostRequest;
import com.coursework.socksapp.model.Size;
import com.coursework.socksapp.model.Socks;
import com.coursework.socksapp.services.SocksService;
import lombok.Data;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Service
@Data
public class SocksServiceImpl implements SocksService {
    private Map<Socks, Integer> socksMap = new HashMap<>();

    @Override
    public void addSocks(List<PostRequest> newSocks){
        for (PostRequest socks : newSocks){
            socksMap.put(socks.getSocks(), socksMap.getOrDefault(socks.getSocks(), 0) + socks.getQuantity());
        }
    }

    @Override
    public String editSocks(PostRequest editedSocks){
        if(socksMap.containsKey(editedSocks.getSocks())){
            if(socksMap.get(editedSocks.getSocks()) >= editedSocks.getQuantity()){
                socksMap.put(editedSocks.getSocks(), socksMap.get(editedSocks.getSocks()) - editedSocks.getQuantity());
                return "Носки ушли со склада.";
            }else{
                return "На складе недостаточно носков такого типа.";
            }
        } else{
            return "На складе нет носков такого типа.";
        }
    }

    @Override
    public int getQuantity(Color color, Size size, int cottonMin, int cottonMax){
        int quantity = 0;
        for(Map.Entry<Socks, Integer> socks : socksMap.entrySet()){
            Socks key = socks.getKey();
            if((color == null || key.getColor() == color) &&
                    (size == null || key.getSize() == size) &&
                    cottonMin <= key.getCottonPart() &&
                    key.getCottonPart() <= cottonMax){
                quantity += socks.getValue();
            }
        }
        return quantity;
    }

    @Override
    public String deleteSocks(PostRequest deletedSocks){
        if(socksMap.containsKey(deletedSocks.getSocks())){
            if(socksMap.get(deletedSocks.getSocks()) >= deletedSocks.getQuantity()){
                socksMap.put(deletedSocks.getSocks(), socksMap.get(deletedSocks.getSocks()) - deletedSocks.getQuantity());
                return "Носки списаны со склада.";
            }else{
                return "На складе недостаточно носков такого типа.";
            }
        } else{
            return "На складе нет носков такого типа.";
        }
    }
}
